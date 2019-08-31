public class MatrixProduct {
   
	private static  int Matrix_size = 100;
	
	static int[][] matrix_A= null;
	static int[][] matrix_B= null;
	
		
	private static int[][] fillupMatrix() {
		int index = 0;
		int[][] matrix = new int[Matrix_size][Matrix_size];
		
		for(int i=0;i<Matrix_size;i++) {
			for(int j=0;j<Matrix_size;j++) {
				matrix[i][j] = index++;
			}
		}
		return matrix;
	}
	public static void main(String[] args) {
		Matrix_size = Integer.parseInt( args[2] );
		System.out.println("\nMatrix size " + Matrix_size );
		matrix_A = fillupMatrix();
		matrix_B = fillupMatrix();
		Task t[][]= new Task[Matrix_size][Matrix_size];		
		if (args.length !=3) {
			System.out.println("Incorrect usage");
			System.out.println(" Usage -> java MainClass <Queue type> " +
					"<number of threads> <number of task>");
			System.exit(-1);
		}
		
		int type = Integer.parseInt(args[0]);
		int nThreads = Integer.parseInt(args[1]);
		int totalTasks = Integer.parseInt(args[2]);
		
		// Get Worker Queue based on users choice
		WorkQueue workQueue = WorkerQueueFactory.getWorkQueue(type, nThreads, totalTasks);
		
		int[][] matrix_R = new int[Matrix_size][Matrix_size];
		
		for(int i=0; i<Matrix_size; i++)
		{
			for(int j=0; j<Matrix_size; j++)
			{
				 t[i][j] = new Tasklet( i,j,  matrix_A, matrix_B, matrix_R );
				workQueue.execute((Runnable)t[i][j]);
			}
		}
		
		// Start the work assigner thread
		//WorkAssignerThread workAssigner = new WorkAssignerThread(workQueue, totalTasks); 
		 
		//Populate the task into worker queue
		//workAssigner.start();

		long startTime = System.currentTimeMillis();
		workQueue.startAllThreads();
		
		/*try {
			workAssigner.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		int tasksDone = workQueue.stopWhenAllTaskFinished();
		
		long endTime = System.currentTimeMillis();
		System.out.println("\nNo of worker threads:"+nThreads);
		System.out.println("---------totaltime-----"+(endTime - startTime));
		System.out.println("---------Num of Tasks Executed-----"+tasksDone);
	}

	/*public int[][] findProduct() {
		Task t[Matrix_size];
		
		return matrix_R;
	}*/


}


class Tasklet implements Task
{
    private int row;
    private int col;
    private int A[][];
    private int B[][];
    private int C[][];
   
    public Tasklet(int row, int col, int A[][], int B[][], int C[][] )
    {
        this.row = row;
        this.col = col;
        this.A = A;
        this.B = B;
        this.C = C;
    }
   
    @Override
    public void run()
    {
      
      
            for(int k = 0; k < B.length; k++)
            {
             C[row][col] += A[row][k] * B[k][col];
            }
                    
    }
  
}

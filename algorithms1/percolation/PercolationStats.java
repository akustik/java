/**
 * Performs statistical tests with the percolation model.
 */
public class PercolationStats {
   
   private double[] threshold; 
   private int nExperiments;
    
   /**
    * Performs T independent computational experiments on an N-by-N grid.
    */
   public PercolationStats(int N, int T)
   {
       nExperiments = T;
       
       if (N <= 0) 
           throw new IllegalArgumentException("The matrix size must be positive");
       if (nExperiments <= 0) 
           throw new IllegalArgumentException(
                "The number of experiments must be positive");  
       
       threshold = new double[nExperiments];
       
       for (int expIdx = 0; expIdx < nExperiments; expIdx++)
       {       
           Percolation model = new Percolation(N);
           do
           {
               model.open(StdRandom.uniform(N) + 1, StdRandom.uniform(N) + 1);       
           } while(!model.percolates());
           
           int openCount = 0;
           for (int i = 1; i <= N; i++)
           {
               for (int j = 1; j <= N; j++)
               {
                   if (model.isOpen(i, j)) 
                       openCount++;
               }       
           }
           
           threshold[expIdx] = ((double) openCount) / ((double) N*N);
       }
   }  
   
   /**
    * sample mean of percolation threshold
    */
   public double mean() 
   {
       return StdStats.mean(threshold);
   }
   
   /**
    * sample standard deviation of percolation threshold
    */
   public double stddev() 
   {
       if (nExperiments > 1)
           return StdStats.stddev(threshold);
       else 
           return Double.NaN;
   } 
 
   /**
    * returns lower bound of the 95% confidence interval
    */
   public double confidenceLo()
   {
       return mean() - 1.96 * stddev() / Math.sqrt(nExperiments);
   }            
   
   /**
    * returns upper bound of the 95% confidence interval
    */
   public double confidenceHi() 
   {
       return mean() + 1.96 * stddev() / Math.sqrt(nExperiments);
   }   
   
   public static void main(String[] args)
   {
       PercolationStats stats = 
           new PercolationStats(
               Integer.parseInt(args[0]), Integer.parseInt(args[1]));
       System.out.println("mean = " + stats.mean());
       System.out.println("stddev = " + stats.stddev());
       System.out.println("95% confidence interval = " 
                              + stats.confidenceLo() + ", " + stats.confidenceHi());
   }  
}
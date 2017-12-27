package test;

import java.util.Random;  
import java.util.concurrent.BlockingQueue;  
import java.util.concurrent.Callable;  
import java.util.concurrent.CompletionService;  
import java.util.concurrent.ExecutionException;  
import java.util.concurrent.ExecutorCompletionService;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.Future;  
import java.util.concurrent.LinkedBlockingDeque;  
  
/** 
 * 将Executor和BlockingQueue功能融合在一起，可以将Callable的任务提交给它来执行， 然后使用take()方法获得已经完成的结果 
 *  
 * @author zhy 
 *  
 */  
public class CompletionServiceDemo  
{  
  
    public static void main(String[] args) throws InterruptedException,  
            ExecutionException  
    {  
        /** 
         * 内部维护11个线程的线程池 
         */  
        ExecutorService exec = Executors.newFixedThreadPool(11);  
        /** 
         * 容量为10的阻塞队列 
         */  
        final BlockingQueue<Future<Integer>> queue = new LinkedBlockingDeque<Future<Integer>>(  
                10);  
        //实例化CompletionService  
        final CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(  
                exec, queue);  
  
        /** 
         * 模拟瞬间产生10个任务，且每个任务执行时间不一致 
         */  
        for (int i = 0; i < 10; i++)  
        {  
            completionService.submit(new Callable<Integer>()  
            {  
                @Override  
                public Integer call() throws Exception  
                {  
                    int ran = new Random().nextInt(1000);  
                    Thread.sleep(ran);  
                    System.out.println(Thread.currentThread().getName()  
                            + " 休息了 " + ran);  
                    return ran;  
                }  
            });  
        }  
          
        /** 
         * 立即输出结果 
         */  
        for (int i = 0; i < 10; i++)  
        {  
            try  
            {     
                //谁最先执行完成，直接返回  
                Future<Integer> f = completionService.take();  
                System.out.println(f.get());  
            } catch (InterruptedException e)  
            {  
                e.printStackTrace();  
            } catch (ExecutionException e)  
            {  
                e.printStackTrace();  
            }  
        }  
  
        exec.shutdown();  
  
    }  
  
}  
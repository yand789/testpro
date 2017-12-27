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
 * ��Executor��BlockingQueue�����ں���һ�𣬿��Խ�Callable�������ύ������ִ�У� Ȼ��ʹ��take()��������Ѿ���ɵĽ�� 
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
         * �ڲ�ά��11���̵߳��̳߳� 
         */  
        ExecutorService exec = Executors.newFixedThreadPool(11);  
        /** 
         * ����Ϊ10���������� 
         */  
        final BlockingQueue<Future<Integer>> queue = new LinkedBlockingDeque<Future<Integer>>(  
                10);  
        //ʵ����CompletionService  
        final CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(  
                exec, queue);  
  
        /** 
         * ģ��˲�����10��������ÿ������ִ��ʱ�䲻һ�� 
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
                            + " ��Ϣ�� " + ran);  
                    return ran;  
                }  
            });  
        }  
          
        /** 
         * ���������� 
         */  
        for (int i = 0; i < 10; i++)  
        {  
            try  
            {     
                //˭����ִ����ɣ�ֱ�ӷ���  
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
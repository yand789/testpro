package test;

import com.google.common.util.concurrent.*;  
import java.util.concurrent.*;  
  
/** 
 * ʹ��guavaʵ���첽�ص� {@link java.util.concurrent.Future} 
 * {@link com.google.common.util.concurrent.ListenableFuture} 
 * {@link com.google.common.util.concurrent.FutureCallback} 
 * 
 * @author landon 
 */  
public class FutureCallbackExample {  
  
    public static void main(String[] args) throws Exception {  
//        nativeFuture();  
//        Thread.sleep(3000L);  
//  
//        guavaFuture();  
//        Thread.sleep(3000L);  
  
        guavaFuture2();  
    }  
  
  
    public static void nativeFuture() throws Exception {  
        // ԭ����Futureģʽ,ʵ���첽  
        ExecutorService nativeExecutor = Executors.newSingleThreadExecutor();  
        Future<String> nativeFuture = nativeExecutor  
                .submit(new Callable<String>() {  
                    @Override  
                    public String call() throws Exception {  
                        // ʹ��sleepģ����ú�ʱ  
                        TimeUnit.SECONDS.sleep(1);  
                        return  "[" + Thread.currentThread().getName() +"]: ������Future���ؽ��" ;  
                    }  
                });  
        // Futureֻʵ�����첽����û��ʵ�ֻص�.���Դ�ʱ���߳�get���ʱ����.���߿�����ѵ�Ա��ȡ�첽�����Ƿ����  
        System.out.println("[" + Thread.currentThread().getName() +"]====>"+ nativeFuture.get());  
  
    }  
    public static void guavaFuture() throws Exception {  
        System.out.println("-------------------------------- ���صķָ��� -----------------------------------");  
        // �õ�ʵ��Ӧ�����ṩ�ص�,���첽������ɺ�,����ֱ�ӻص�.��������guava�ṩ���첽�ص��ӿ�,����ܶ�.  
        ListeningExecutorService guavaExecutor = MoreExecutors  
                .listeningDecorator(Executors.newSingleThreadExecutor());  
        final ListenableFuture<String> listenableFuture = guavaExecutor  
                .submit(new Callable<String>() {  
  
                    @Override  
                    public String call() throws Exception {  
                        TimeUnit.SECONDS.sleep(1);  
                        return  "[" + Thread.currentThread().getName() +"]: guava��Future���ؽ��";  
                    }  
                });  
  
        // ע�������,���첽�������ʱ����ָ�����̳߳���ִ��ע��ļ�����  
        listenableFuture.addListener(new Runnable() {  
            @Override  
            public void run() {  
                try {  
                    String logTxt = "[" + Thread.currentThread().getName() +"]: guava�Է��ؽ�������첽CallBack(Runnable):"  
                            + listenableFuture.get();  
                    System.out.println(logTxt);  
                } catch (Exception e) {  
                }  
            }  
        }, Executors.newSingleThreadExecutor());  
  
        // ���߳̿��Լ���ִ��,�첽��ɺ��ִ��ע��ļ���������.  
        System.out.println( "[" + Thread.currentThread().getName() +"]: guavaFuture1ִ�н���");  
    }  
  
    public static void guavaFuture2() throws Exception {  
        System.out.println("-------------------------------- ���صķָ��� -----------------------------------");  
        // ����ListenableFuture,guava���ṩ��FutureCallback�ӿ�,�����˵���ӷ���һЩ.  
        ListeningExecutorService guavaExecutor2 = MoreExecutors  
                .listeningDecorator(Executors.newSingleThreadExecutor());  
        final ListenableFuture<String> listenableFuture2 = guavaExecutor2  
                .submit(new Callable<String>() {  
                    @Override  
                    public String call() throws Exception {  
                        TimeUnit.SECONDS.sleep(1);  
                        String logText = "[" + Thread.currentThread().getName() +"]: guava��Future���ؽ��";  
                        System.out.println(logText);  
                        return logText;  
                    }  
                });  
  
        // ע������û��ָ��ִ�лص����̳߳�,��������Կ�����<span style="color:#FF0000;">Ĭ���Ǻ�ִ���첽�������߳���ͬһ��.</span>  
        Futures.addCallback(listenableFuture2, new FutureCallback<String>() {  
                    @Override  
                    public void onSuccess(String result) {  
                        String logTxt = "[" + Thread.currentThread().getName() +"]=======>�Իص������"+result+"������FutureCallback,�����ԣ������Ǻͻص���������߳�Ϊͬһ���߳�";  
                        System.out.println(logTxt);  
                    }  
                    @Override  
                    public void onFailure(Throwable t) {  
                    }  
                }  
        );  
        // ���߳̿��Լ���ִ��,�첽��ɺ��ִ��ע��ļ���������.  
        System.out.println( "[" + Thread.currentThread().getName() +"]: guavaFuture2ִ�н���");  
    }  
}  
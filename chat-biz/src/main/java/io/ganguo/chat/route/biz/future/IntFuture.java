package io.ganguo.chat.route.biz.future;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by James on 2015/11/12 0012.
 * 借口执行异步操作
 */
public class IntFuture {

    private static final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));

    public static void exec(final FutureService futureService,final Object... args){

        ListenableFuture<Object> explosion = executorService.submit(
                new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        Object o = null;
                        try{
                            o = futureService.handler(args);
                        }catch (Exception e){

                        }
                        return o;
                    }
                });


        Futures.addCallback(explosion, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object o) {

                futureService.onSuccess(o,args);
            }

            @Override
            public void onFailure(Throwable throwable) {
                futureService.onFailure(throwable,args);
            }
        });



    }

}

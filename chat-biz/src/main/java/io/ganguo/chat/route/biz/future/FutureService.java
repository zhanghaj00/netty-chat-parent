package io.ganguo.chat.route.biz.future;

/**
 * Created by James on 2015/11/12 0012.
 */
public interface FutureService<T> {

        /**
         * 执行的方法
         *
         * @param args
         * @return
         */
        T handler(Object... args);

        /**
         * 成功后执行
         *
         * @param result 执行的结果
         * @param args   传递参数（执行方法中的args）
         */
        void onSuccess(Object result, Object... args);

        /**
         * 失败后执行
         *
         * @param args      传递参数（执行方法中的args）
         * @param throwable
         */
        void onFailure(Throwable throwable, Object... args);

}

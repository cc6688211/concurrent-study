package pers.cc.tools;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.tools
 * @类名称： SleepTools
 * @类描述：【类描述】线程休眠公共类
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月21日上午11:08:47
 */
public class SleepTools {
	
	/**
	 * 按秒休眠
	 * @param seconds 秒数
	 */
    public static final void second(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
    
    /**
     * 按毫秒数休眠
     * @param seconds 毫秒数
     */
    public static final void ms(int seconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}

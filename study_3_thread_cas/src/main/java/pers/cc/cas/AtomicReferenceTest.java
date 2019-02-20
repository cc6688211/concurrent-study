package pers.cc.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 可以原子更新的对象引用
 * 
 * @author cc
 *
 */
public class AtomicReferenceTest {
    /**
     * 创建一个原子更新的对象
     */
    static AtomicReference < User > atomicReference = new AtomicReference <>();

    public static void main(String[] args) {
        User cc = new User("cc", 18);
        /**
         * 初始化对象
         */
        atomicReference.set(cc);
        User cccc = new User("cc", 30);
        /**
         * 更换对象属性
         */
        atomicReference.compareAndSet(cc, cccc);
        System.out.println(atomicReference.get().getAge());
        System.out.println(cc.getAge());
    }

    static class User {
        private String name;

        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}

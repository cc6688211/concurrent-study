package pers.cc.curriculum2.forkJion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 遍历打印指定目录下指定类型的文件
 * 
 * @author cc
 *
 */
public class FindDirsTypeFiles extends RecursiveAction {
    private File path;// 当前任务需要搜寻的目录

    private String fileType;// 当前任务需要搜寻的目录

    public FindDirsTypeFiles(File path, String fileType) {
        this.path = path;
        this.fileType = fileType;
    }

    @Override
    protected void compute() {
        List < FindDirsTypeFiles > subTasks = new ArrayList < FindDirsTypeFiles >();
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                // 如果是路径就新建一个子任务
                if (file.isDirectory()) {
                    subTasks.add(new FindDirsTypeFiles(file, fileType));
                }
                else {
                    // 遇到文件，检查
                    if (file.getAbsolutePath().endsWith(fileType)) {
                        System.out.println("文件：" + file.getAbsolutePath());
                    }
                }
            }
            if (!subTasks.isEmpty()) {
                // 将若有子任务集合并将结果合并
                for (FindDirsTypeFiles subTask : invokeAll(subTasks)) {
                    subTask.join();// 等待子任务执行完成
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // 创建线程池
        ForkJoinPool pool = new ForkJoinPool();
        FindDirsTypeFiles task = new FindDirsTypeFiles(new File("D:/.m2"),
                "jar");
        pool.execute(task);// 异步调用
        System.out.println("Task is Running......");
        Thread.sleep(1);
        int otherWork = 0;
        for (int i = 0; i < 100; i++) {
            otherWork = otherWork + i;
        }
        System.out.println("Main Thread done sth......,otherWork=" + otherWork);
        task.join();// 阻塞的方法，无返回
        System.out.println("Task end");
    }
}

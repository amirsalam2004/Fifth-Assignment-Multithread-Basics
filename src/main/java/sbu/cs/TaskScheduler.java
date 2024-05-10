package sbu.cs;

import java.util.ArrayList;
import java.util.List;


public class TaskScheduler
{
    public static class Task implements Runnable
    {
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */
        String taskName;
        int processingTime;

        public Task(String taskName, int processingTime) {
            this.taskName = taskName;
            this.processingTime = processingTime;
        }
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */

        @Override
        public void run() {
            /*
            TODO
                Simulate utilizing CPU by sleeping the thread for the specified processingTime
             */
            try {
                Thread.sleep(processingTime);
            }
            catch (InterruptedException e){}
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks = new ArrayList<>();

        /*
        TODO
            Create a thread for each given task, And then start them based on which task has the highest priority
            (highest priority belongs to the tasks that take more time to be completed).
            You have to wait for each task to get done and then start the next task.
            Don't forget to add each task's name to the finishedTasks after it's completely finished.
         */
        boolean check=true;
        while (check){
            check=false;
            for(int i=1;i<tasks.size();i++){
                if(tasks.get(i).processingTime>tasks.get(i-1).processingTime){
                    tasks.add(i-1,tasks.get(i));
                    tasks.remove(i+1);
                    check=true;
                }
            }
        }
        for(int i=0;i<tasks.size();i++){
            try {
                Thread thread=new Thread(tasks.get(i));
                thread.start();
                thread.join();
                finishedTasks.add(tasks.get(i).taskName);
            }
            catch (InterruptedException e){
            }
        }
        return finishedTasks;
    }

    public static void main(String[] args) {
        // Test your code here
    }
}

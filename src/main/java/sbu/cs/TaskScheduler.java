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
            try {
                Thread.sleep(processingTime);
            }
            catch (InterruptedException e){}
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks = new ArrayList<>();
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

package sbu.cs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatrixMultiplication {

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable
    {
        List<List<Integer>> tempMatrixProduct;
        int index_x;
        int index_y;
        List<List<Integer>> matrix_1;
        List<List<Integer>> matrix_2;
        int maximum_x;
        int maximum_y;
        public BlockMultiplier(int index_x,int index_y,List<List<Integer>> matrix_1,
                               List<List<Integer>> matrix_2,int maximum_x,int maximum_y) {
            this.matrix_1=matrix_1;
            this.matrix_2=matrix_2;
            this.index_x=index_x;
            this.index_y=index_y;
            this.maximum_x=maximum_x;
            this.maximum_y=maximum_y;
            this.tempMatrixProduct=new ArrayList<List<Integer>>();
        }
        public int multiplication(List<Integer> x,List<Integer> y){
            int sum=0;
            for(int i=0;i<x.size();i++){
                sum+=x.get(i)*y.get(i);
            }
            return sum;
        }
        @Override
        public void run() {
            for(int i=index_x;i<=maximum_x;i++){
                List<Integer> row=new ArrayList<>();
                for (int j=index_y;j<=maximum_y;j++){
                    row.add(multiplication(matrix_1.get(i),matrix_2.get(j)));
                }
                tempMatrixProduct.add(row);
            }
        }
    }
    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B)
    {
        List<List<Integer>> ParallelizeMatMul=new ArrayList<List<Integer>>();
        int rows;
        int columns;
        List<List<Integer>> matrix_2=new ArrayList<List<Integer>>();
        for(int i=0;i<matrix_B.get(0).size();i++){   //Moving rows and columns in matrix_B
            List<Integer> column = new ArrayList<>();
            for(int j=0;j<matrix_B.size();j++){
                column.add(matrix_B.get(j).get(i));
            }
            matrix_2.add(column);
        }
        rows=matrix_A.size();
        columns=matrix_2.size();
        try {
            BlockMultiplier bock1 = new BlockMultiplier(0, 0, matrix_A, matrix_2, rows / 2 - 1, columns / 2 - 1);
            BlockMultiplier bock2 = new BlockMultiplier(0, columns / 2, matrix_A, matrix_2, rows / 2 - 1, columns - 1);
            BlockMultiplier bock3 = new BlockMultiplier(rows / 2, 0, matrix_A, matrix_2, rows - 1, columns / 2 - 1);
            BlockMultiplier bock4 = new BlockMultiplier(rows / 2, columns / 2, matrix_A, matrix_2, rows - 1, columns - 1);
            Thread thread1 = new Thread(bock1);
            Thread thread2 = new Thread(bock2);
            Thread thread3 = new Thread(bock3);
            Thread thread4 = new Thread(bock4);
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            for(int i=0;i<bock1.tempMatrixProduct.size();i++){
                List<Integer> row=new ArrayList<>();
                row.addAll(bock1.tempMatrixProduct.get(i));
                row.addAll(bock2.tempMatrixProduct.get(i));
                ParallelizeMatMul.add(row);
            }
            for (int i=0;i<bock3.tempMatrixProduct.size();i++){
                List<Integer> row=new ArrayList<>();
                row.addAll(bock3.tempMatrixProduct.get(i));
                row.addAll(bock4.tempMatrixProduct.get(i));
                ParallelizeMatMul.add(row);
            }
        }catch (InterruptedException e){}

        return ParallelizeMatMul;
    }

    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        List<List<Integer>> a=new ArrayList<List<Integer>>();
        List<List<Integer>> b=new ArrayList<List<Integer>>();
        for (int i=0;i<2;i++) {
            List<Integer> row=new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                int x = in.nextInt();
                row.add(x);
            }
            a.add(row);
        }
        for (int i=0;i<3;i++) {
            List<Integer> row=new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                int x = in.nextInt();
                row.add(x);
            }
            b.add(row);
        }
        System.out.println(ParallelizeMatMul(a,b));
    }
}

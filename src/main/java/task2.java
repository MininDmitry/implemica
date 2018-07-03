import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.fill;

public class task2 {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("how many tests do you want to conduct");
        int с = scanner.nextInt();
        for(int gh = 0;gh<с;gh++) {
            List<String> sitiesname = new ArrayList<String>();
            System.out.println("Please write number of sities");
            int num = scanner.nextInt();
            int[][] rezultmas = new int[num * num * num][3];
            int mas[][] = addmas(rezultmas, sitiesname, num);
            // all previous actions were aimed at forming an array showing all possible paths and their prices in a correct format
            System.out.println("Which city do you want to go to from?");
            int sitstart = scanner.nextInt() - 1;
            int sitfinish = scanner.nextInt() - 1;
            int wayrez[] = new int[mas.length * mas.length];
            int count = 0;
            for (int i = 0; i < wayrez.length; i++) {
                int lk = 0;
                int pk = 0;

                if (select(mas, sitstart, sitfinish, pk, 880, lk)) {

                    wayrez[i] = select1(mas, sitstart, sitfinish, pk, 0,lk,120) ;//- mas[sitstart][2];
                    lk++;

                }else
                    {
                        pk++;
                        lk=0;
                    }

            }
            Arrays.sort(wayrez);
            for (int i = 0; i < wayrez.length; i++) {
                if (wayrez[i] > 0) {
                    System.out.println(wayrez[i]);

                }
            }
        }
    }
    /* Алгоритм Дейкстры за O(V^2) */
     static public void dijkstra(int start,int[][]graph,int vNum) {
         int INF = 100000000;
         boolean[] used = new boolean [vNum]; // массив пометок
                 int[] dist = new int [vNum]; // массив расстояния. dist[v] = минимальное_расстояние(start, v)
         fill(dist, INF); // устанаавливаем расстояние до всех вершин INF
         dist[start] = 0; // для начальной вершины положим 0
         for (;;) {
             int v = -1;
             for (int nv = 0; nv < vNum; nv++) // перебираем вершины
                 if (!used[nv] && dist[nv] < INF && (v == -1 || dist[v] > dist[nv])) // выбираем самую близкую непомеченную вершину
                      v = nv;
                 if (v == -1) break; // ближайшая вершина не найдена
                  used[v] = true; // помечаем ее
             for (int nv = 0; nv < vNum; nv++)
                 if (!used[nv] && graph[v][nv] < INF) // для всех непомеченных смежных
                     dist[nv] = min(dist[nv], dist[v] + graph[v][nv]); // улучшаем оценку расстояния (релаксация)
         }
         }
    static public boolean select(int[][]mas,int sitstart,int sitfin,int num,int prev,int lk){
        boolean rez = false;
        for(int i =num;i<mas.length;i++){
            if(mas[i][0]==sitstart&&mas[i][1]==sitfin){if(lk>0){lk--; }else{rez= true; break;}}
            if(rez==true)break;
            if(mas[i][0]==sitstart&&mas[i][1]!=prev&&mas[i][1]!=sitfin){
                rez = select(mas,mas[i][1],sitfin,0,mas[i][0],lk);break;
            }

        }
        return rez;
    }
    static public int select1(int[][]mas,int sitstart,int sitfin,int num,int rez,int lk,int prev){
        for(int i =num;i<mas.length;i++){
            if(mas[i][0]==sitstart&&mas[i][1]==sitfin){if(lk>0){lk--;  }else{rez+=mas[i][2] ;break;}}
            if(mas[i][0]==sitstart&&mas[i][1]!=sitfin&&mas[i][1]!=prev){
                rez+=mas[i][2];
                rez += select1(mas,mas[i][1],sitfin,++i,rez,lk,mas[i][0]);
                }
        }
        return rez;
    }
    static public int[][] addmas(int[][] rezultmas,List<String>sitiesname,int num){
        int countrel=0;
        int allcount=0;
        for(int l =0;l<num;l++) {
            System.out.println("Please write names town ");
            sitiesname.add(scanner.next());
            System.out.println("Please write count reletives ");
            countrel = scanner.nextInt();
            allcount+=countrel;
            for (int i = allcount-countrel; i < allcount;i++ ){
                rezultmas[i][0] = l;
                System.out.println("Current number sities" + rezultmas[i][0]+1);
                System.out.println("Please write reletives first the number of the city to be contacted, after, the cost of communication");
                rezultmas[i][1]= (scanner.nextInt())-1;
                rezultmas[i][2]= scanner.nextInt();
            }

        }
        System.out.println();
        int[][]mas  = new int[allcount][3];
        for(int i =0;i<allcount;i++){
            mas[i][0]=rezultmas[i][0];
            System.out.print(sitiesname.get(mas[i][0])+" ");
            mas[i][1]=rezultmas[i][1];
            System.out.print(sitiesname.get(mas[i][1])+" ");
            mas[i][2]=rezultmas[i][2];
            System.out.print(mas[i][2]+" ");
            System.out.println();
        }
        return mas;
    }

}

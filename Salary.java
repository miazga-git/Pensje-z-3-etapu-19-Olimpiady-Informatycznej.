import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Salary{

    private static final int MAXN = 1000010;
    int[] parents = new int[MAXN];
    int[] salaries = new int[MAXN];
    int[] usedSalaries = new int[MAXN];
    int[] numberOfChildren = new int[MAXN];
    int[] oneChild = new int[MAXN];
    int[] children = new int[MAXN];
    int numberOfEmployees = 0;//number of workers
    List list= new LinkedList();

    void readValues(){
        Scanner scanner = new Scanner(System.in);
        numberOfEmployees = scanner.nextInt();//get number of workers
        for (int i = 1; i <= numberOfEmployees; i++) {
            parents[i] = scanner.nextInt();//get parent of i-worker
            salaries[i] = scanner.nextInt();//get salary of i-worker
            if (parents[i] == i){ salaries[i] = numberOfEmployees;}
        }
    }

    void findLeafes() {
        for (int i = 1; i <= numberOfEmployees; ++i) {
            if(parents[i]!=i)
                children[parents[i]] = children[parents[i]] + 1;
        }
        for (int i = 1; i <= numberOfEmployees; i++){
            if (children[i] == 0) {
                list.add(i);

            }
        }
    }
    void numberOfChildrensWithNotSpecifiedSalaries(){
        while (!list.isEmpty()) {
            int child = (Integer)list.remove(0);
            int parent = parents[child];
            if (salaries[child] == 0) {
                --children[parent];
                if (children[parent] == 0)
                    list.add(parent);
                numberOfChildren[parent] += numberOfChildren[child] + 1;
            }
        }
    }

    void findUsedSalaries() {
        for (int i = 1; i <= numberOfEmployees; i++){
            if (salaries[i] != 0)
                usedSalaries[salaries[i]] = i;
            else if (oneChild[parents[i]] == 0)
                oneChild[parents[i]] = i;
            else oneChild[parents[i]] = -1;//-1 when node has more than 1 child
        }

    }

    void assignSalaries() {
        int i = 1;
        int numberOfFree = 0;
        int numberOfPossible = 0;
        while (i <= numberOfEmployees ) {
            while (i <= numberOfEmployees  && usedSalaries[i] == 0) {

                i++;
                numberOfFree++;
                numberOfPossible++;
            }

            while (i <= numberOfEmployees  && usedSalaries[i] != 0) {
                int node = usedSalaries[i];
                int salary = i;
                numberOfFree -= numberOfChildren[node];
                if (numberOfFree == 0) {
                    while (numberOfPossible > 0 && oneChild[node] > 0) {
                        numberOfPossible--;
                        while (usedSalaries[salary] > 0) salary--;
                        node = oneChild[node];
                        salaries[node] = salary;
                        usedSalaries[salary] = node;
                    }
                    numberOfPossible = 0;
                }
                if (numberOfChildren[node] != 0)
                    numberOfPossible = 0;
                i++;
            }
        }
    }

    void printSalaries(){
        for (int i = 1; i <= numberOfEmployees; i++)
            System.out.println(salaries[i]);
    }

    public static void main(String[] args) {
        Salary s= new Salary();
            s.readValues();
            s.findLeafes();
            s.numberOfChildrensWithNotSpecifiedSalaries();
            s.findUsedSalaries();
            s.assignSalaries();
            s.printSalaries();
        }
    }
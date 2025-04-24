

public class ArrayList<T extends Comparable<T>> implements List<T> {



    public T[] array;
    private boolean isSorted;

    public ArrayList() {
        this.array = (T[]) new Comparable[2];
        this.isSorted = true;
    }

    @Override
    public boolean add(T element){
        if(element == null){
            return false;
        }
        else{
            if (size() == array.length){
                T[] temp = (T[]) new Comparable[array.length * 2];
                for(int i = 0; i<array.length; i++){
                    temp[i] = array[i];
                }
                array = temp;
            }
            int index = size();
            array[index] = element;
            return true;
        }
    }

    @Override
    public boolean add(int index, T element) {
        if (element == null || index < 0 || index >= size()) {
            return false;
        }
        else if(array[index] == null){
            array[index] = element;
        }
        else{
            if (size() == array.length){
                T[] temp = (T[]) new Comparable[array.length * 2];
                for(int i = 0; i<array.length; i++){
                    temp[i] = array[i];
                }
                array = temp;
            }
            for(int i = 0; i < size(); i++){
                array[size() - i] = array[size() - (i + 1)];
            }
            array[index] = element;



        }
        return true;
    }

    @Override
    public void clear(){
        array = (T[]) new Comparable[2];
    }

    @Override
    public T get(int index){
        if(index > size() || index < 0){
            return null;
        }
        else{
            return array[index];
        }
    }

    @Override
    public int indexOf(T element){
        if(element == null){
            return -1;
        }
        else{
            int index = 0;
            boolean look = false;
            for(int i = 0; i < size(); i++){
                index++;
                if(isSorted() == true) {
                    break;
                }
                if(array[i] == element){
                    look = true;
                    break;
                }
            }
            if(look == true){
                return index;
            }
            else{
                return -1;
            }
        }
    }

    @Override
    public boolean isEmpty(){
        if(size() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int size(){
        int num = 0;
        for(int i = 0; i<array.length; i++){
            num++;
        }
        return num;
    }

    @Override
    public void sort(){
        if(isSorted() == true || size() <= 1){
            return;
        }
        else{
            for(int i = 0; i < size() - 1; i++){
                T temp = array[i];
                boolean look = false;
                int switcher = 0;
                for(int j = i + 1; j < size(); j++){
                    if(array[j].compareTo(temp) < 0){
                        temp = array[j];
                        look = true;
                        switcher = j;
                    }
                }
                if(look == true){
                    array[switcher] = array[i];
                    array[i] = temp;
                }
            }
            isSorted = true;
        }
        return;
    }

    @Override
    public T remove(int index){
        if (index < 0 || index >= size()){
            return null;
        }
        else{
            T remove = array[index];
            for(int i = index; i < size(); i++){
                array[i] = array[i + 1];
            }
            return remove;
        }
    }

    @Override
    public void equalTo(T element){
        if(element == null){
            return;
        }
        else {
            for (int i = 0; i < size(); i++) {
                if(array[i] != element){
                    remove(i);
                    i--;
                }
            }
        }
    }

    @Override
    public void reverse(){
        if(size() == 0 || size() == 1){
            return;
        }
        else{
            T switcher;
            for(int i = 0; i < size() / 2; i++){
                switcher = array[i];
                array[i] = array[size() - (i + 1)];
                array[size() - (i + 1)] = switcher;
            }
        }
    }

    @Override
    public void merge(List<T> otherList){
        if(otherList == null){
            return;
        }
        else{
            ArrayList<T> other = (ArrayList<T>) otherList;
            this.sort();
            other.sort();
            ArrayList<T> tempo = new ArrayList<>();
            tempo.array = (T[]) new Comparable[size() + other.size()];

           int partA = 0;
           int partB = 0;

            while(tempo.array.length != tempo.size()){
                if(array[partA] == null){
                    tempo.add(other.array[partA]);
                    partA++;
                }
                else if(other.array[partB] == null){
                    tempo.add(array[partA]);
                    partA++;

                }
                else{
                    if(array[partA].compareTo(other.array[partB]) < 0){
                        tempo.add(array[partA]);
                        partA++;
                    }
                    else if(array[partA].compareTo(other.array[partB]) > 0){
                        tempo.add(other.array[partB]);
                    }
                    else{
                        tempo.add(array[partA]);
                        tempo.add(other.array[partB]);
                    }
                }

            }
            array = tempo.array;
        }
    }

    @Override
    public boolean rotate(int n){
        if(n <= 0 || size() <= 1 || n >= size()){
            return false;
        }
        else{
            if(n == size()){
                return true;
            }
            else{
                for(int i = 0; i < n; i++){
                    add(0, remove(size() - 1));
                }
                return true;
            }
        }
    }

    @Override
    public String toString(){
        String total = "";
        if(size() == 0){
            total = "Array list is empty";
        }
        else{
            for(int i = 0; i < size(); i++){
                total += array[i] + "/n";
            }
        }
        return total;
    }

    @Override
    public boolean isSorted(){
        return isSorted;
    }

}
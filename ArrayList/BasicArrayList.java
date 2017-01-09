/** 
Zach Scott
cpe 102 hw 2
**/
import java.util.NoSuchElementException;
public class BasicArrayList implements BasicList{
   public static final int DEFAULT_CAPACITY = 10;
   private int size;
   private Object[] list;
   
   public BasicArrayList(){
      size = 0;
      list = new Object[DEFAULT_CAPACITY];
   }
   public BasicArrayList(int capacity){
      size = 0;
      if(capacity < 10){
         list = new Object[DEFAULT_CAPACITY];
      }
      else{
         list = new Object[capacity];
      }
   }
   
   public void add(int index, Object o){
      Object swap1;
      Object swap2;
      if(index>size){
         throw new IndexOutOfBoundsException();
      }
      if(index<0){
         throw new IndexOutOfBoundsException();
      }
      if(size==list.length){
         Object[] list2 = new Object[list.length * 2];
         int k;
         for(k=0;k<size;k++){
            list2[k] = list[k];
         }
         this.list = list2;
      }
      if(index == size){      
         this.add(o);
         
      }
      else{
         int i ;
         swap1 = list[index];
         list[index] = o;
         for(i=index+1;i<size+1;i++){
            swap2 = swap1;
            swap1 = list[i];
            list[i]=swap2;
           
         }
         size++;
      }
   
      
   }
   public void add(Object o){
      if(size == list.length){
         Object[] list2 = new Object[list.length * 2];
         int i = 0;
         for(i=0;i<size;i++){
            list2[i] = list[i];
         }
         list2[size] = o;
         this.list = list2;
         size++;
      }
      else{
         list[size] = o;
         size++;
      }
   }
   public int capacity(){
      return list.length;
   }
   public void clear(){
      int i =0;
      for(i=0;i<size;i++){
         list[i] = null;
      }
      size = 0;
   }
   public boolean contains(Object o){
      int i;
      for(i=0;i<size;i++){
         if(o.equals(list[i])){
            return true;
         }
      }
      return false;
   }
   public Object get(int index){
      if(index>size){
         throw new IndexOutOfBoundsException();
      }
      if(index==size){
         throw new IndexOutOfBoundsException();
      }
      if(index<0){
         throw new IndexOutOfBoundsException();
      }
      return list[index];
   }
   public int indexOf(Object element){
      int i;
      int k = 0;
      boolean has = false;
      for(i=0;i<size;i++){
         if(element.equals(list[i])){
            has = true;
            k=i;
         }
        
      }
      if(has==false){
         throw new NoSuchElementException();
      }
      return k;
   
   }
   public Object remove(int index){
      int i;
      Object o;
      if(index >= size){
         throw new IndexOutOfBoundsException();
      }
      if(index<0){
         throw new IndexOutOfBoundsException();
      }
      if(index==size-1){
         o = list[index];
         list[index] = null;
         size--;
         return o;
      }
      else{
         o=list[index];
         for(i=index;i<size;i++){
            if(i!=size-1){
               list[i] = list[i+1];
            }
            else{
               list[i] = null;
            }
         }
         size--;
         return o;
      }
            
   }
   public Object set(int index, Object o){
      if(index>=size){
         throw new IndexOutOfBoundsException();
      }
      if(index < 0){
         throw new IndexOutOfBoundsException();
      }
      Object o2 = list[index];
      list[index] = o;
      return o2;
   }
   public int size(){
      return size;
   }
   public void trimToSize(){
      int cap;
      if(size<10){
         cap = DEFAULT_CAPACITY;
      }
      else{
         cap = size;
      }
      if(list.length != cap){
      
         Object[] list2 = new Object[cap];
         int i = 0;
         for(i=0;i<size;i++){
            list2[i] = list[i];
         }
         this.list = list2;
         }
      }
      

}
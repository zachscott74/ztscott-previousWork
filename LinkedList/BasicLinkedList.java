/** Zach Scott
cpe102 hw6 **/

import java.lang.Iterable;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class BasicLinkedList<E> implements BasicList<E>,Iterable<E>{
   private class Node{
      public E data;
      public Node next, prev;
   }
   private Node head = new Node();
   private int size = 0;
   public BasicLinkedList(){
      head.data = null;
      head.next = head;
      head.prev = head;
   }
   public void add(E element){
      size++;
      Node n = new Node();
      n.data = element;
      n.prev = head.prev;
      n.next = head;
      head.prev.next = n;
      head.prev = n;
   }
   public void add(int index, E element){
      if(index > size){
         throw new IndexOutOfBoundsException();
      }
      if(index<0){
         throw new IndexOutOfBoundsException();
      }
      Node n = new Node();
      n.data = element;
      Node temp = head;
      int i;
      for(i=-1;i<index;i++){
         temp = temp.next;
      }
      n.next = temp;
      n.prev = temp.prev;
      temp.prev.next = n;
      temp.prev = n;
      size++;    
   }
   private class MyIt implements BasicListIterator<E>{
      private Node cursor = head;
      public boolean hasNext(){
        return cursor.next != head;
      }
      public E next(){
         if(!hasNext()){
            throw new NoSuchElementException();
         }
         cursor = cursor.next;
         return cursor.data;
      }
      public boolean hasPrevious(){
         return cursor != head;
      }
      public E previous(){
         if(!hasPrevious()){
            throw new NoSuchElementException();
         }
         E data = cursor.data;
         cursor = cursor.prev;
         return data;
      }
      public void remove(){
         throw new UnsupportedOperationException();
      }
   }
   public BasicListIterator<E> basicListIterator(){
      return new MyIt();
   }
   public void clear(){
      head.next = head;
      head.prev = head;
      size=0;
   }
   public boolean contains(E element){
      Node temp = head;
      if(element == null){
         while(temp.next != head){
            temp = temp.next;
            if(temp.data == null){
               return true;
            }
            
         }
         return false;
      }
      temp = head;
      while(temp.next != head){
         temp = temp.next;
         if(element.equals(temp.data)){
            return true;
         }
      
      }
      return false;
   }
   public E get(int index){
      if(index >= size){
         throw new IndexOutOfBoundsException();
      }
      if(index<0){
         throw new IndexOutOfBoundsException();
      }
      Node temp = head;
      int i;
      for(i=-1;i<index;i++){
         temp = temp.next;
      }
      return temp.data;

   }
   public int indexOf(E element){
      Node temp = head;
      if(element == null){
         int in = -1;
         while(temp.next != head){
            temp = temp.next;
            in++;
            if(temp.data == null){
               return in;
            }
         }
         throw new NoSuchElementException();
      }
      temp = head;
      int i = -1;
      while(temp.next != head){
         temp = temp.next;
         i++;
         if(element.equals(temp.data)){
            return i;
         }
      }
      throw new NoSuchElementException();
         
   }
   public Iterator<E> iterator(){
      return new MyIt();
   }
   public E remove(int index){
      if(index >= size){
         throw new IndexOutOfBoundsException();
      }
      if(index<0){
         throw new IndexOutOfBoundsException();
      }
      Node temp = head;
      int i;
      for(i=-1;i<index;i++){
         temp = temp.next;      
      }
      temp.prev.next = temp.next;
      temp.next.prev = temp.prev;
      size--;
      return temp.data;

   }
   public E set(int index, E element){
      if(index >= size){
         throw new IndexOutOfBoundsException();
      }
      if(index<0){
         throw new IndexOutOfBoundsException();
      }
      Node n = new Node();
      n.data = element;
      Node temp = head;
      int i;
      for(i=-1;i<index;i++){
         temp = temp.next;      
      }
      temp.prev.next = n;
      temp.next.prev = n;
      n.next = temp.next;
      n.prev = temp.prev;
      return temp.data;
   }
   public int size(){
      return size;
   }
}
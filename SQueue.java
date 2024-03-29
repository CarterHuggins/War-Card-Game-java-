package ass1;
import java.util.Random;

public class SQueue<T> implements QueueInterface<T>, Shufflable {
	private T [] theArray;  // internal representation of a queue
	
	//this is an index in the array where to read the data 
	//at the front of the queue
	private int read; 
	
	//this is an index in the array where to write the next element 
	//at the end of the queue
	private int write;
	
	//variable to store the number of slots in theArray
	//just for consistency, technically not required, 
	//because you can get the capacity of theArray from theArray.length
	private int capacity;
	
	//this stores the size of the queue: 
	// the total number of actual elements in the queue
	private int size;
	
	//initialization of the internal array of predefined capacity
	public SQueue (int capacity)	{		
		this.capacity = capacity;
		this.theArray = (T[]) new Object[capacity];
		this.read = 0;
		this.write = 0;
		this.size = 0;
	}	
	
	public int getSize() {
		return this.size;
	}
	
	//add element to the end of the queue
	//wrap around the array as long as you did not reach the front 
	// of the queue
	// if no element can be added - throw exception
	public void enqueue(T newEntry) {
		if (isFull()) {
			throw new FullQueueException("The queue is full");
		} else {
			theArray[write] = newEntry;
			write = (write + 1) % capacity;
			size++;
		}
	}
	
	
	
	  
	//remove and return an element from the frony of the queue
	//because the array is circular - this object is at position read
	//if queue is empty - throw exception
	//if you remove element at position i of the array, 
	// please set theArray[read] = null; 
	// though technically not needed,
	//this will help you to see empty slots in theArray
	public T dequeue() {

		if (isEmpty()) {
			throw new IllegalStateException("The queue is empty");

		}

		else {
			T removedItem = theArray[read];
			theArray[read] = null;
			read = (read + 1) % capacity;
			size --;

			return removedItem;

		}
		
		//replace the line above with your code
	}
	  
	//Returns the entry at the front of this queue.
	//throw  EmptyQueueException if the queue is empty. 
	public T getFront() {

		if (isEmpty()){
			throw EmptyQueueException();
		}
		else {
			return theArray[read];
		}
		//replace the line above with your code
	}
	  
	//Detect whether this queue is empty.
	public boolean isEmpty() {	

		return size == 0;
		
		//replace the line above with your code
	}
	
	//Detect whether this queue is full.
	//this should prevent read and write indexes to become equal
	
	public boolean isFull() {
		
			return size == capacity;
	
		
	}
	  
	//Removes all entries from this queue. 
	//think: can it potentially be done in one operation?
	public void clear() {

		for (int i = 0; i < capacity; i++) {

			theArray[i] = null;
		}


		read = 0;
		write = 0;
		size = 0;
		
	}
	
	//implement the random reordering of the elements in theARray
	//try to come up with an efficient algorithm by yourself.
	//if not - check out Fisher-Yates shuffle algorithm, 
	//also known as the Knuth shuffle algorithm
	//be careful - you only need to shuffle elements 
	//between read and write index - not including the unoccupied slots of the array.
	public void shuffle() {
		if (size <= 1) {
			return;
		}
	
		Random rand = new Random();
	
		for (int i = write - 1; i > read; i--) {
			int randomIndex = read + rand.nextInt(i - read + 1);
	
			
			T temp = theArray[i];
			theArray[i] = theArray[randomIndex];
			theArray[randomIndex] = temp;
		}
	}
	
	//this is used for debugging and testing
	//please do not change!
	public String toString() {
		StringBuilder result = new StringBuilder("SQueue: the array [" + theArray[0] );
        for (int i = 1; i < capacity; i++) {
        	result.append(", ");
        	result.append(theArray[i]);
        }
        result.append("] ");        
       
        result.append("Capacity:" + this.capacity);
        result.append(" Size:" + this.size);
        result.append(" Read:" + this.read);
        result.append(" Write:" + this.write);
        return result.toString();
	}

}

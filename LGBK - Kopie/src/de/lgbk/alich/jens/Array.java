package de.lgbk.alich.jens;

import de.lgbk.alich.jens.intellij.ISortArray;
import de.lgbk.alich.jens.intellij.Method;
import org.jarcraft.library.time.StopWatch;


public abstract class Array<T> implements ISortArray
{
    private Object[] elements;

    private StopWatch stopWatch;

    // creates an array whith a length of 10
    public Array() {
        elements = new Object[10];
        stopWatch = new StopWatch();
    }

    // creates an array whith a defined length
    public Array(int length) {
        elements = new Object[length];
        stopWatch = new StopWatch();
    }

    @Method("fillArray")
    // fills the Array with random numbers between 1 und 100
    public void fill() {
        fill(100);
    }

    @Method("fillArray")
    // fills the Array with random numbers between 1 und upperLimit
    public abstract void fill(int upperLimit);

    @Method("printArray")
    // prints the array on the terminal
    public void printArray() {
        System.out.print("[ ");
        for (Object element : elements) {
            System.out.print(printElement(convert(element)) + " ");
        }
        System.out.println("]");
    }

    public abstract String printElement(T element);

    /*
     * searches a specific value in the array
     * and returns the adress of the position,
     * if the value could not be found the
     * value -1 is returned.
     */
    public int search(T searchedValue) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == searchedValue) {
                return i;
            }
        }
        return -1;
    }

    // exchanges the content of two adresses in the array
    public void exchange(int index0, int index1) {
        Object bufferValue = elements[index1];
        elements[index1] = elements[index0];
        elements[index0] = bufferValue;
    }

    // exchanges the content of three adresses in the array (0 => 1, 1 => 2, 2 => 0)
    public void exchange(int index0, int index1, int index2) {
        exchange(new int[]{index0, index1, index2});
    }

    // exchanges the content of n adresses in the array (0 => 1, 1 => 2, n-1 => 0)
    public void exchange(int[] indexes) {
        Object bufferValue = elements[indexes[indexes.length-1]];
        for (int i = indexes.length-1; i > 0; i--) {
            elements[indexes[i]] = elements[indexes[i-1]];
        }
        elements[indexes[0]] = bufferValue;
    }

    // reverses the order of the elements in the array
    public void reverse()
    {
        for(int i = 0; i<elements.length/2; i++)
        {
            exchange(i ,elements.length-i-1);
        }
    }

    // sorts the array with the bubblesort process
    @Method("bubbleSort")
    public long bubblesort()
    {
        stopWatch.start();
        //ausgeben();
        for(int i=0; i<elements.length-1; i++)
        {
            boolean exchanged = false;
            for(int j=0; j<elements.length-1-i; j++)
            {
                if(objectBiggerAs(elements[j], elements[j+1]))
                {
                    exchange(j,j+1);
                    exchanged = true;
                }
            }

            if (!exchanged) {
                stopWatch.stop();
                return stopWatch.getTime();
            }
            //ausgeben();
        }
        stopWatch.stop();
        return stopWatch.getTime();
    }

    @Method("insertionSort")
    // sorts the array with the insertionsort process
    public long insertionsort()
    {
        stopWatch.start();
        //ausgeben();
        for(int i=1; i<elements.length; i++)
        {
            Object exchangeValue = elements[i];
            int exchangeIndex = i;
            for(int j=i; j>0; j--)
            {
                if(objectSmallerAs(exchangeValue, elements[j-1]))
                {
                    exchangeIndex = j-1;
                    elements[j] = elements[j-1];
                } else break;
            }
            elements[exchangeIndex] = exchangeValue;
            //ausgeben();
        }
        stopWatch.stop();
        return stopWatch.getTime();
    }

    // determines the position of the smalest element from a specific index in the array
    public int minimalPosition(int beginningIndex)
    {
        int minimalPosition = beginningIndex;
        for (int i=beginningIndex+1; i < elements.length; i++)
        {
            if (objectSmallerAs(elements[i], elements[minimalPosition]))
            {
                minimalPosition = i;
            }
        }
        return minimalPosition;
    }

    @Method("selectionSort")
    // sorts the array with the selstionsort process
    public long selectionsort()
    {
        stopWatch.start();
        //ausgeben();
        for(int i=0; i<elements.length-1; i++)
        {
            exchange(i,minimalPosition(i));
            //ausgeben();
        }
        stopWatch.stop();
        return stopWatch.getTime();
    }

    @Method("mergeSort")
    // sorts the array with the mergesort process
    public long mergesort() {
        stopWatch.start();
        split(0, elements.length);
        stopWatch.stop();
        return stopWatch.getTime();
    }

    // sorts the defined array with the mergesort process
    private void split(int startIndex, int length) {
        if (length <= 1) return;
        int leftStartIndex = startIndex;
        int leftLength = length / 2;
        int rightStartIndex = startIndex + leftLength;
        int rightLength = length - leftLength;
        split(leftStartIndex, leftLength);
        split(rightStartIndex, rightLength);
        merge(leftStartIndex, leftLength, rightStartIndex, rightLength);
    }

    // merges the two predefined sorted array to one sorted array
    private void merge(int leftStartIndex, int leftLength, int rightStartIndex, int rightLength) {
        Object[] newNumbers = new Object[leftLength + rightLength];
        int counterLeft = 0;
        int counterRight = 0;

        while (counterLeft != leftLength || counterRight != rightLength) {
            if (counterLeft != leftLength && (counterRight == rightLength || (objectBiggerAs(elements[rightStartIndex + counterRight], elements[leftStartIndex + counterLeft])))) {
                newNumbers[counterLeft + counterRight] = elements[leftStartIndex + counterLeft];
                counterLeft++;
            } else {
                newNumbers[counterLeft + counterRight] = elements[rightStartIndex + counterRight];
                counterRight++;
            }
        }
        override(leftStartIndex, newNumbers);
    }

    // overrides the elements of the array with the new numbers
    private void override(int start, Object[] newNumbers) {
        for (int i = 0; i < newNumbers.length; i++) {
            elements[start + i] = newNumbers[i];
        }
    }

    @Method("quickSort")
    // sorts the array with the quicksort process
    public long quicksort() {
        stopWatch.start();
        pivot(0, elements.length);
        stopWatch.stop();
        return stopWatch.getTime();
    }

    //  sorts the defined array with the quicksort process
    private void pivot(int start, int length) {
        if (length <= 1) return;
        int pivotIndex = start;
        for (int i = start; i < start + length; i++) {
            if (objectBiggerAs(elements[pivotIndex], elements[i])) {
                exchange(pivotIndex, pivotIndex+1, i);
            }
        }
        pivot(start, pivotIndex - start);
        pivot(pivotIndex+1, length - (pivotIndex+1 - start));
    }

    private boolean equals(Object elementOne, Object elementTwo) {
        return elementOne.equals(elementTwo);
    }

    public boolean objectEquals(T elementOne, T elementTwo) {
        return elementOne.equals(elementTwo);
    }

    private boolean objectSmallerAs(Object elementOne, Object elementTwo) {
        return smallerAs(convert(elementOne), convert(elementTwo));
    }

    public abstract boolean smallerAs(T elementOne, T elementTwo);

    private boolean objectBiggerAs(Object elementOne, Object elementTwo) {
        return biggerAs(convert(elementOne), convert(elementTwo));
    }

    public boolean biggerAs(T elementOne, T elementTwo) {
        return !(equals(elementOne, elementTwo) || smallerAs(elementOne, elementTwo));
    }

    @SuppressWarnings("unchecked")
    private T convert(Object element) {
        return (T) element;
    }

    public T getElement(int index) {
        return convert(elements[index]);
    }

    public int getLength() {
        return elements.length;
    }

    public void setElement(T element, int index) {
        elements[index] = element;
    }
}
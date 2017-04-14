package de.lgbk.alich.jens;

import java.util.Random;

public class NumberArray
{
    private int[] numbers;

    // creates an array whith a length of 10
    public NumberArray() {
        numbers = new int[10];
    }

    // creates an array whith a defined length
    public NumberArray(int length) {
        numbers = new int[length];
    }

    // fills the Array with random numbers between 1 und 100
    public void fill() {
        Random random = new Random();
        for (int i=0; i<numbers.length; i++)
            numbers[i] = random.nextInt(100)+1;
    }

    // fills the Array with random numbers between 1 und upperLimit
    public void fill(int upperLimit)
    {
        Random random = new Random();
        for (int i=0; i<numbers.length; i++)
            numbers[i] = random.nextInt(upperLimit)+1;
    }

    // prints the array on the terminal
    public void printArray() {
        System.out.print("[ ");
        for (int i=0; i<numbers.length; i++)
        {
            System.out.print(numbers[i]+"  ");
        }
        System.out.println("]");
    }

    // replaces the value of an index with a new value
    public void einfuegen(int index, int newValue) {
        numbers[index] = newValue;
    }

    /*
     * searches a specific value in the array
     * and returns the adress of the position,
     * if the value could not be found the
     * value -1 is returned.
     */
    public int search(int searchedValue) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == searchedValue) {
                return i;
            }
        }
        return -1;
    }

    // exchanges the content of two adresses in the array
    public void exchange(int index0, int index1) {
        int bufferValue = numbers[index1];
        numbers[index1] = numbers[index0];
        numbers[index0] = bufferValue;
    }

    // exchanges the content of three adresses in the array (0 => 1, 1 => 2, 2 => 0)
    public void exchange(int index0, int index1, int index2) {
        exchange(new int[]{index0, index1, index2});
    }

    // exchanges the content of n adresses in the array (0 => 1, 1 => 2, n-1 => 0)
    public void exchange(int[] indexes) {
        int bufferValue = numbers[indexes[indexes.length-1]];
        for (int i = indexes.length-1; i > 0; i--) {
            numbers[indexes[i]] = numbers[indexes[i-1]];
        }
        numbers[indexes[0]] = bufferValue;
    }

    // reverses the order of the elements in the array
    public void reverse()
    {
        for(int i = 0; i<numbers.length/2; i++)
        {
            exchange(i ,numbers.length-i-1);
        }
    }

    // sorts the array with the bubblesort process
    public void bublesort()
    {
        //ausgeben();
        for(int i=0; i<numbers.length-1; i++)
        {
            boolean exchanged = false;
            for(int j=0; j<numbers.length-1-i; j++)
            {
                if(numbers[j]>numbers[j+1])
                {
                    exchange(j,j+1);
                    exchanged = true;
                }
            }

            if (!exchanged) return;
            //ausgeben();
        }
    }

    // sorts the array with the insertionsort process
    public void insertionsort()
    {
        //ausgeben();
        for(int i=1; i<numbers.length; i++)
        {
            int exchangeValue = numbers[i];
            int exchangeIndex = i;
            for(int j=i; j>0; j--)
            {
                if(exchangeValue<numbers[j-1])
                {
                    exchangeIndex = j-1;
                    numbers[j] = numbers[j-1];
                } else break;
            }
            numbers[exchangeIndex] = exchangeValue;
            //ausgeben();
        }

    }

    // determines the position of the smalest element from a specific index in the array
    public int minimalPosition(int beginningIndex)
    {
        int minimalPosition = beginningIndex;
        for (int i=beginningIndex+1; i < numbers.length; i++)
        {
            if (numbers[i] < numbers[minimalPosition])
            {
                minimalPosition = i;
            }
        }
        return minimalPosition;
    }

    // sorts the array with the selstionsort process
    public void selectionsort()
    {
        //ausgeben();
        for(int i=0; i<numbers.length-1; i++)
        {
            exchange(i,minimalPosition(i));
            //ausgeben();
        }
    }

    // sorts the array with the mergesort process
    public void mergesort() {
        split(0, numbers.length);
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
        int[] newNumbers = new int[leftLength + rightLength];
        int counterLeft = 0;
        int counterRight = 0;

        while (counterLeft != leftLength || counterRight != rightLength) {
            if (counterLeft != leftLength && (counterRight == rightLength || (numbers[leftStartIndex + counterLeft] <= numbers[rightStartIndex + counterRight]))) {
                newNumbers[counterLeft + counterRight] = numbers[leftStartIndex + counterLeft];
                counterLeft++;
            } else {
                newNumbers[counterLeft + counterRight] = numbers[rightStartIndex + counterRight];
                counterRight++;
            }
        }
        override(leftStartIndex, newNumbers);
    }

    // overrides the elements of the array with the new numbers
    private void override(int start, int[] newNumbers) {
        for (int i = 0; i < newNumbers.length; i++) {
            numbers[start + i] = newNumbers[i];
        }
    }

    // sorts the array with the quicksort process
    public void quicksort() {
        pivot(0, numbers.length);
    }

    //  sorts the defined array with the quicksort process
    private void pivot(int start, int length) {
        if (length <= 1) return;
        int pivotIndex = start;
        for (int i = start; i < start + length; i++) {
            if (numbers[i] <= numbers[pivotIndex]) {
                exchange(pivotIndex, pivotIndex+1, i);
            }
        }
        pivot(start, pivotIndex - start);
        pivot(pivotIndex+1, length - (pivotIndex+1 - start));
    }
}
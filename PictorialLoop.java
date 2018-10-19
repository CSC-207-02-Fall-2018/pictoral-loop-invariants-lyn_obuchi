package loopInvariant;

/**
 * 
 * @author Dongze, Obuchi 
 * 
 * This class contains multiple recursive and
 * non-recursive methods required of the Pictorial Loop Invariants Lab.
 *
 */
public class PictorialLoop {

	/**
	 * moves an element of a to a position in the middle of the array and rearranges
	 * the remainder of the array segment a[left] ... a[right]
	 * 
	 * @param a, an array of integers
	 * @param left, index of the left-bound of the part of array to be partitioned
	 * @param right, index of the right-bound of the part of array to be partitioned
	 * @return middle, the index of the item that is put into its correct position.
	 * @Precondition None additional
	 * @Postcondition see return
	 */
	static int partition(int a[], int left, int right) {
		int l_spot = left + 1; // the first item to be partitioned
		int r_spot = right;
		int pivot = a[left];
		int middle;
		int temp;

		while (r_spot >= l_spot) {
			// search left to find small array item
			while ((r_spot >= l_spot) && (pivot <= a[r_spot])) {
				r_spot--;
			}

			// search right to find large array item
			while ((r_spot >= l_spot) && (pivot >= a[l_spot])) {
				l_spot++;
			}

			// swap large left item and small right item, if needed
			if (r_spot > l_spot) {
				temp = a[l_spot];
				a[l_spot] = a[r_spot];
				a[r_spot] = temp;
			}
		}
		// put pivot in its correct place.
		a[left] = a[r_spot];
		a[r_spot] = pivot;
		middle = r_spot;
		return middle;
	}

	/**
	 * returns the kth smallest element in an array a
	 * 
	 * @param a, an array of integers
	 * @param k, an indicator that tells we should find the kth smallest element in
	 *        the array;
	 * @return the kth smallest element in an array i
	 * @Precondition 0 <= k <= a.length()-1
	 * @Postcondition see return.
	 */
	static int select(int a[], int k) {
		return selectHelper(a, k, 0, a.length - 1);
	}

	/**
	 * returns the kth smallest element in an array a
	 * 
	 * @param a, an array of integers
	 * @param k, an indicator that tells we should find the kth smallest element in
	 *        the array;
	 * @param left, index of the left-bound of the array to be selected
	 * @param right, index of the right-b/ and a[right+1]..a[last], provided these
	 *        segments contain >= 2 items ound of the array to be selected
	 * @return the kth smallest element in an array i
	 * @Precondition 0 <= k <= a.length()-1 ;
	 * @Postcondition see return.
	 */
	static int selectHelper(int a[], int k, int left, int right) {
		if (a.length == 1) {
			return a[1];
		}

		int middle = partition(a, left, right);

		if (k == ((middle) - left + 1)) {
			return a[middle];
		}

		if (k <= ((middle) - left)) {
			return selectHelper(a, k, left, (middle - 1));
		} else {
			return selectHelper(a, (k - ((middle) - left) - 1), (middle + 1), right);
		}
	}

	/**
	 * returns the median element in an array a
	 * 
	 * @param a, an array of integers
	 * @return the median element in the array a
	 * @Precondition None additional ;
	 * @Postcondition see return.
	 */
	static int median(int a[]) {
		return selectHelper(a, (a.length / 2) + 1, 0, a.length - 1);
	}

	/**
	 * sorts the array a using the quicksort algorithm
	 * 
	 * @param a, an array of integers
	 * @Precondition None additional
	 * @Postcondition a is sorted in an ascending order;
	 */
	static void quicksort(int a[]) {
		quicksortKernel(a, 0, a.length - 1);
	}

	/**
	 * sorts the array a using the quicksort algorithm
	 * 
	 * @param a, an array of integers
	 * @param left, index of the left-bound of the array to be sorted
	 * @param right, index of the right-bound of the array to be sorted
	 * @Precondition None additional
	 * @Postcondition a is sorted in an ascending order;
	 */
	static void quicksortKernel(int a[], int left, int right) {
		if (left < right) {
			int pivotLocation = partition(a, left, right);

			if (pivotLocation > left) {
				quicksortKernel(a, left, pivotLocation - 1);
			}
			if (pivotLocation < right) {
				quicksortKernel(a, pivotLocation + 1, right);
			}
		}
	}

	/**
	 * creates colors that can be used
	 */
	public enum Color {
		red, white, blue; // each is an instance of Color
	}

	/**
	 * sorts an array of colors to represent the Dutch National Flag
	 * 
	 * @param a, an array of colors
	 * @Precondition None additional
	 * @Postcondition the colors in the array are sorted in a red, white then blue
	 *                order
	 */
	static void invariantA(Color colors[]) {
		int whiteLeft = 0;
		int blueLeft = 0;
		int leftover = 0;
		Color temp;

		while (leftover < colors.length) {
			temp = colors[leftover];
			if (temp == Color.blue) {
				// Swap temp (blue) with first blue.
				colors[leftover] = colors[blueLeft];
				colors[blueLeft] = temp;

			} else if (temp == Color.white) {
				// Swap the first leftover (white) with the first blue (right after last white)
				colors[leftover] = colors[blueLeft];
				colors[blueLeft] = temp;
				blueLeft++; // update blueFirst.

			} else if (temp == Color.red) {
				// put the first blue at leftover, the first white at blueLeft, then temp (the
				// first leftover (red)) at whiteLeft.
				colors[leftover] = colors[blueLeft];
				colors[blueLeft] = colors[whiteLeft];
				colors[whiteLeft] = temp;
				blueLeft++; // update blueLeft
				whiteLeft++; // update whiteLeft
			}
			leftover++;
		}
	}

	/**
	 * sorts an array of colors to represent the Dutch National Flag
	 * 
	 * @param a, an array of colors
	 * @Precondition None additional
	 * @Postcondition the colors in the array are sorted in a red, white then blue
	 *                order
	 */
	static void invariantD(Color colors[]) {
		int leftoverLast = colors.length - 1;
		int redRight = colors.length - 1;
		int whiteRight = colors.length - 1;
		Color temp;

		while (leftoverLast >= 0) {
			temp = colors[leftoverLast];
			if (temp == Color.red) {
				// Swap temp (red) with last red.
				colors[leftoverLast] = colors[redRight];
				colors[redRight] = temp;
				
			} else if (temp == Color.white) {
				// Swap the last leftover (white) with last blue (right after last white)
				colors[leftoverLast] = colors[redRight];
				colors[redRight] = temp;
				redRight--; // update redRight.
				
			} else if (temp == Color.blue) {
				// put the first blue at leftover, the first white at blueLeft, then temp (the
				// first leftover (red)) at whiteLeft.
				colors[leftoverLast] = colors[redRight];
				colors[redRight] = colors[whiteRight];
				colors[whiteRight] = temp;
				redRight--; // update blueLeft
				whiteRight--; // update whiteLeft
			}
			leftoverLast--;
		}

	}

	/**
	 * Main testing method
	 * 
	 * @param args, not used
	 * @Precondition none
	 * @Postcondition printout a detailed testing about the procedures in the class
	 */
	public static void main(String args[]) {
		int[] arr1 = { 4, 2, 3, 5, 7 };
		int[] arr2 = { 4, 2, 3, 5, 7 };
		int[] arr3 = { 5, 7, 1, 3, 6, 4, 9 };
		int[] arr4 = { 5, 7, 1, 3, 6, 4, 9 };
		int[] arr5 = { 4, 2, 3, 5, 7 };
		int[] arr6 = { 5, 7, 1, 3, 6, 4, 9 };
		int[] arr7 = { 7, 6, 5, 4, 3 };
		int[] arr8 = { 3, 5, 1, 4, 4, 8, 0 };

		// General tests for partition, select, and median procedures with different arrays
		System.out.print("arr1:  ");
		for (int i : arr1) {
			System.out.print(i);
		}
		System.out.println();
		System.out.println("The pivot should be located at position #" + partition(arr1, 0, arr1.length - 1));
		System.out.print("The second smallest element is ");
		System.out.println(select(arr2, 2));
		System.out.print("The median element is ");
		System.out.println(median(arr5));

		System.out.println();

		System.out.print("arr3:  ");
		for (int i : arr3) {
			System.out.print(i);
		}
		System.out.println();
		System.out.println("The pivot should be located at position #" + partition(arr1, 0, arr1.length - 1));

		System.out.print("arr4:  ");
		for (int i : arr4) {
			System.out.print(i);
		}
		System.out.println();
		System.out.print("The seventh smallest element is ");
		System.out.println(select(arr4, 7));

		System.out.print("arr6:  ");
		for (int i : arr6) {
			System.out.print(i);
		}
		System.out.println();
		System.out.print("The median element is ");
		System.out.println(median(arr6));

		System.out.println();
		System.out.print("arr7:  ");
		for (int i : arr7) {
			System.out.print(i);
		}
		System.out.println();
		System.out.print("The sorted array is ");
		quicksort(arr7);
		for (int i : arr7) {
			System.out.print(i);
		}

		System.out.println();
		System.out.print("arr8:  ");
		for (int i : arr8) {
			System.out.print(i);
		}
		System.out.println();
		System.out.print("The sorted array is ");
		quicksort(arr8);
		for (int i : arr8) {
			System.out.print(i);
		}
		System.out.println();

		// Test for Dutch flag problem solutions A and D (procedure invariantA and invariantD)
		System.out.println();
		Color[] flagA = { Color.blue, Color.blue, Color.blue, Color.blue, Color.red, Color.red, Color.white };
		System.out.print("flagA:  ");
		for (Color c : flagA) {
			System.out.print(c + "  ");
		}
		invariantA(flagA);
		System.out.println("\nAfter sorting,");
		System.out.print("flagA:  ");
		for (Color c : flagA) {
			System.out.print(c + "  ");
		}
		
		System.out.println("\n");
		Color[] flagD = { Color.white, Color.blue, Color.blue, Color.red, Color.white, Color.red, Color.white };
		System.out.print("flagD:  ");
		for (Color c : flagD) {
			System.out.print(c + "  ");
		}
		invariantD(flagD);
		System.out.println("\nAfter sorting,");
		for (Color c : flagD) {
			System.out.print(c + "  ");
		}
	}
}

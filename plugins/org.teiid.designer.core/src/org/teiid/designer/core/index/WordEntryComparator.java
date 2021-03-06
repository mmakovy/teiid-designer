/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.core.index;

import java.util.Comparator;

import org.teiid.designer.core.index.IndexConstants;
import org.teiid.designer.core.index.WordEntry;

/**
 * WordEntryComparator
 *
 * @since 8.0
 */
public class WordEntryComparator implements Comparator {

    /**
     * Constructs an instance of this class given the indicies of the parameters
     * to sort on, and whether the sort should be in ascending or descending
     * order.
     */
    public WordEntryComparator() {}

    /*
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
	public int compare(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null && o2 != null) {
			return -1;
		} else if (o1 != null && o2 == null) {
			return 1;
		}

        // Cast input objects to Lists...
        WordEntry entry1 = (WordEntry)o1;
        WordEntry entry2 = (WordEntry)o2;

        char[] word1= entry1.getWord();
        char[] word2= entry2.getWord();
        
        // compare only till the length of the smaller word
        int minLength = StrictMath.min(word1.length, word2.length);

        int k = 0;
        while (true && k < minLength) {
            char char1 = word1[k];
            char char2 = word2[k];

            if (char1 != char2) {
                return char1 - char2;
            }
            // compare only upto the third delimiter in the line
            if(char1 == IndexConstants.RECORD_STRING.RECORD_DELIMITER && k > 1) {
                break;
            }
            k++;
        }
        return 0;
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }

        if(anObject == null || anObject.getClass() != this.getClass()) {
            return false;
        }

        return true;
    }
}

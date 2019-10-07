package cryptotool.algorithm;

/**
 * Implementation of RC4 stream cipher
 *
 * @author Samrand Haji
 * @author Razan Hidayet
 * @author Iman Abdullahad
 */
public class RC4 {
    
    /**
     * String of alphanumeric to find index of each character for XOR-ing.
    */
    private final String indexing;
    
    /**
     * SIZE = number of permutation of RC4 cryptography algorithm
     */
    private final int SIZE;
    
    public RC4(){
        this.indexing = getIndexing();
        this.SIZE = 32;
    }
    
     /**
     * Initialize SBOX with given key. Key-scheduling algorithm
     *
     * @param key is the key to be used for S-Box
     * @return sbox integer array after apply (SIZE) time permutation
     * @see <a href="http://en.wikipedia.org/wiki/RC4#Key-scheduling_algorithm_.28KSA.29">Wikipedia. Init sbox</a>
     */
    private int[] initSBox(int[] key){
        int [] sbox = new int[SIZE];
        for(int i = 0 ; i < SIZE ; i++)
            sbox[i] = i;
        int j = 0;
        for( int i = 0 ; i < SIZE ; i++ ){
            j = (j + sbox[i] + key[i % key.length]) % SIZE;
            swap(i,j,sbox);
        }
        return sbox;
    }
    
     /**
     * encrypt given String. Be aware, that you must initialize the key, before using
     * crypt.
     *
     * @param P the plain text to be encrypted. 
     * @param K the key to process the encryption
     * @return encrypted plain text.
     * @see <a href="http://en.wikipedia.org/wiki/RC4#Pseudo-random_generation_algorithm_.28PRGA.29">Pseudo-random generation algorithm</a>
     */
    public String encrypt(String P,String K){
        int [] plain = toIntArray(P);
        int [] key = toIntArray(K);
        int [] sbox = initSBox(key);
        int i=0,j=0,rand;
        for(int iteration = 0 ; iteration < plain.length ; iteration++){
            i = ( i + 1 ) % SIZE;
            j = ( j + sbox[i] ) % SIZE;
            swap(i,j,sbox);
            rand = sbox[(sbox[i] + sbox[j]) %  SIZE];
            plain[iteration] =(plain[iteration]^ rand) % indexing.length();
        }
        return toStringArray(plain);
        
    }
    
    /**
     * Simple function to swap S-Box content.
     * @param index1 specify the first index to be replaced.
     * @param index2 specify the second index to be replaced.
     * @param array specify the array that will be replaced.
     */
    private void swap(int index1,int index2,int [] array){
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    
    
    /**
     * Function used to change Array of Integer to alphanumeric based on Indexing string.
     * 
     * @param array the integer array to be converted to alphanumeric
     * @return String of alphanumeric
     */
    private String toStringArray(int [] array){
        String temp = "";
        for(int i = 0 ; i < array.length ; i++)
            temp += getIndex(array[i]);
        return temp;
    }
    
    /**
     * Function used to change alphanumeric to array of integer based on Indexing string, 
     * in order to be easier to work with encryption alphanumeric to alphanumeric instead of ASCII code.
     * @param str the string to be converted to integer array
     * @return Integer array
     */
    private int[] toIntArray(String str){
        int length = str.length();
        int[] temp = new int[length];
        for(int i = 0 ; i < length ; i++)
            temp[i] = getIndex(str,i);
        return temp;
    }
    
    /**
     * Function that specify the index of alphabet and number inside Indexing string.
     * @param index index of the alphabet/number.
     * @return the character of that index.
     */
    private String getIndex(int index){
        return indexing.charAt(index) + "";
    }
    
    /**
     * Function used to get Index of a/an alphabet/number of a string inside indexing string.
     * 
     * @param str the string of alphabet/number
     * @param index index of the character inside the string
     * @return index of the character that has been sent to the function.
     */
    private int getIndex(String str,int index){
        return indexing.indexOf(str.charAt(index));
    }
    
    /**
     * Function to fill Indexing string with alphabet, number & whitespace automatically,
     * You can change it based on your need, you can add symbols or remove number.
     * @return String of alphanumeric that has been generated automatically.
     */
    private String getIndexing(){
        String temp = " ";
        
        for(char character = 'a'; character <= 'z'; character++)
            temp += character;
        
        for(int number = 0 ; number <= 9 ; number++)
            temp += number;
        
        return temp;
    }
}

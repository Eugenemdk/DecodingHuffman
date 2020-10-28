package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.awt.Event.F1;
import static java.lang.Math.log;

// A Tree node
class Node
{
    char ch;
    int freq;
    Node left = null, right = null;

    Node(char ch, int freq)
    {
        this.ch = ch;
        this.freq = freq;
    }

    public Node(char ch, int freq, Node left, Node right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}

class Main
{
    // traverse the Huffman Tree and store Huffman Codes in a map.
    public static void encode(Node root, String str, Map<Character, String> huffmanCode)
    {
        if (root == null)
            return;

        // found a leaf node
        if (root.left == null && root.right == null) {
            huffmanCode.put(root.ch, str);
        }

        encode(root.left, str + '0', huffmanCode);
        encode(root.right, str + '1', huffmanCode);
    }

    // traverse the Huffman Tree and decode the encoded string
    public static int decode(Node root, int index, StringBuilder sb)
    {
        if (root == null)
            return index;

        // found a leaf node
        if (root.left == null && root.right == null)
        {
            System.out.print(root.ch);
            return index;
        }

        index++;

        if (sb.charAt(index) == '0')
            index = decode(root.left, index, sb);
        else
            index = decode(root.right, index, sb);

        return index;
    }

    // Builds Huffman Tree and huffmanCode and decode given input text
    public static void buildHuffmanTree(String text)
    {
        // count frequency of appearance of each character
        // and store it in a map
        Map<Character, Integer> freq = new HashMap<>();
        for (char c: text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        // Create a priority queue to store live nodes of Huffman tree
        // Notice that highest priority item has lowest frequency
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value->value.freq));

        // Create a leaf node for each character and add it
        // to the priority queue.
        for (Map.Entry<Character,Integer> entry : freq.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }

        // do till there is more than one node in the queue
        while (priorityQueue.size() != 1)
        {
            // Remove the two nodes of highest priority
            // (lowest frequency) from the queue
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();

            // Create a new internal node with these two nodes as children
            // and with frequency equal to the sum of the two nodes
            // frequencies. Add the new node to the priority queue.
            int sum = left.freq + right.freq;
            priorityQueue.add(new Node(' ', sum, left, right));
        }

        // root stores pointer to root of Huffman Tree
        Node root = priorityQueue.peek();

        // traverse the Huffman tree and store the Huffman codes in a map
        Map<Character, String> huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);

        // print the Huffman codes
        System.out.println("Huffman Codes are : " + huffmanCode);
        //System.out.println("Original string was : " + text);

        // print encoded string
        StringBuilder sb = new StringBuilder();
        for (char c: text.toCharArray()) {
            sb.append(huffmanCode.get(c));
        }
        System.out.println("Encoded string is : " + sb);

        // traverse the Huffman Tree again and this time
        // decode the encoded string
        int index = -1;
        System.out.print("Decoded string is: ");
        while (index < sb.length() - 2) {
            index = decode(root, index, sb);
        }
    }
public static String encodingStroke(String text) {

            // count frequency of appearance of each character
            // and store it in a map
            Map<Character, Integer> freq = new HashMap<>();
            for (char c : text.toCharArray()) {
                freq.put(c, freq.getOrDefault(c, 0) + 1);
            }

            // Create a priority queue to store live nodes of Huffman tree
            // Notice that highest priority item has lowest frequency
            PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.freq));

            // Create a leaf node for each character and add it
            // to the priority queue.
            for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
                priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
            }

            // do till there is more than one node in the queue
            while (priorityQueue.size() != 1) {
                // Remove the two nodes of highest priority
                // (lowest frequency) from the queue
                Node left = priorityQueue.poll();
                Node right = priorityQueue.poll();

                // Create a new internal node with these two nodes as children
                // and with frequency equal to the sum of the two nodes
                // frequencies. Add the new node to the priority queue.
                int sum = left.freq + right.freq;
                priorityQueue.add(new Node(' ', sum, left, right));
            }

            // root stores pointer to root of Huffman Tree
            Node root = priorityQueue.peek();

            // traverse the Huffman tree and store the Huffman codes in a map
            Map<Character, String> huffmanCode = new HashMap<>();
            encode(root, "", huffmanCode);

            // print the Huffman codes
            System.out.println("Huffman Codes are : " + huffmanCode);
            System.out.println("Original string was : " + text);

            // print encoded string
            StringBuilder sb = new StringBuilder();
            for (char c : text.toCharArray()) {
                sb.append(huffmanCode.get(c));
            }

        String encode= sb.toString();
            return encode;
        }



    public static void calcEntropy(String text){
        Map<Character,Integer>chars=new HashMap<>();
        HashMap<Character,Integer>repeats=new HashMap<>();
        int count=0;
        int sum=0;
        double h=0.0;
        for (int i = 0; i < text.length(); i++) {
            chars.put(text.charAt(i),i);
        }
        for (int i = 0; i < chars.size(); i++) {
            for(Map.Entry<Character,Integer>entry:chars.entrySet()){
                for (int j = 0; j < text.length(); j++) {
                    if(entry.getKey()==text.charAt(j)){
                        count++;
                    }
                }
                repeats.put(entry.getKey(),count);
                count=0;
            }
        }
            for(Map.Entry<Character,Integer>entry:repeats.entrySet()){
               double p=1.0*entry.getValue()/text.length();
                //double e= log(p)/log(2);
                //if(entry.getValue()>0)
                h+=-p*log(p)/log(2);
            }
            for(Map.Entry<Character,Integer>entry:repeats.entrySet()){
                double p=1.0*entry.getValue()/text.length();
                double e= log(p)/log(2);
                System.out.println(p*e);
            }
        System.out.print(chars.keySet());
        System.out.println();
        System.out.println(chars.size());
        System.out.println();
        for(Map.Entry<Character,Integer>entry:repeats.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
            sum+=entry.getValue();
        }
        System.out.println("Entropy of dat message is:"+h);
    }
public static void writeToFile(String str) throws IOException {
    BufferedWriter writer=new BufferedWriter(new FileWriter("C:\\Users\\Евгений\\IdeaProjects\\DecodingHuffman\\newText.txt"));
    writer.write(str);
    writer.close();
    }

    public static String readFile(String path) throws IOException {
        Scanner input = new Scanner(new File( path));
        String message=input.next();
        while(input.hasNextLine()) {
            message+=input.nextLine();
        }
        return message;
    }
    public static void main(String[] args) throws IOException {
      String message=readFile("C:\\Users\\Евгений\\IdeaProjects\\DecodingHuffman\\text.txt");
        writeToFile(encodingStroke(message));
      String decodedMessage=readFile("C:\\Users\\Евгений\\IdeaProjects\\DecodingHuffman\\newText.txt");

        buildHuffmanTree(message);
        System.out.println();



        //calcEntropy(message);

        calcEntropy(decodedMessage);
    }
}
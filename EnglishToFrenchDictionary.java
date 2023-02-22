package Translation;



	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.FileReader;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.time.Duration;
	import java.time.Instant;
	import java.util.HashMap;
	import java.util.HashSet;
	import java.util.Map;
	import java.util.Set;

	public class EnglishToFrenchDictionary {
	    
	    public static void main(String[] args) {
	        // Read input text file
	       String inputFilePath = "E://New folder/t8.shakespeare.txt";
	        Set<String> replacedWords = new HashSet<>();
	        Map<String, Integer> replaceCount = new HashMap<>();
	        Instant start = Instant.now();
	        try (BufferedReader inputReader = new BufferedReader(new FileReader(inputFilePath))) {
	            // Read find words list file
	            String findWordsFilePath = "D://text/find_words.txt";
	            Set<String> findWordsList = new HashSet<>();
	            try (BufferedReader findWordsReader = new BufferedReader(new FileReader(findWordsFilePath))) {
	                String line;
	                while ((line = findWordsReader.readLine()) != null) {
	                    findWordsList.add(line.trim().toLowerCase());
	                }
	            }
	            // Read dictionary file
	            String dictionaryFilePath = "F://New folder/french_dictionary.csv";
	            Map<String, String> dictionary = new HashMap<>();
	            try (BufferedReader dictReader = new BufferedReader(new FileReader(dictionaryFilePath))) {
	                String line;
	                while ((line = dictReader.readLine()) != null) {
	                    String[] parts = line.split(",");
	                    dictionary.put(parts[0].trim().toLowerCase(), parts[1].trim());
	                }
	            }
	            // Process each line in the input file
	            String outputFilePath = "E://New folder/t8.shakespeare - Copy.txt";
	            try (BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFilePath, true))) {
	                String line;
	                while ((line = inputReader.readLine()) != null) {
	                    // Split the line into individual words
	                    String[] words = line.split("\\s+");
	                    // Replace words in the find words list with their French translations
	                    for (int i = 0; i < words.length; i++) {
	                        String word = words[i].toLowerCase();
	                        if (findWordsList.contains(word)) {
	                            String frenchWord = dictionary.get(word);
	                            if (frenchWord != null) {
	                                words[i] = frenchWord;
	                                replacedWords.add(word);
	                                replaceCount.put(word, replaceCount.getOrDefault(word, 0) + 1);
	                            }
	                        }
	                    }
	                    // Write the modified line to the output file
	                    String newLine = String.join(" ", words);
	                    outputWriter.write(newLine);
	                    outputWriter.newLine();
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Instant end = Instant.now();
	        Duration timeElapsed = Duration.between(start, end);
	        long memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	        System.out.println("Unique words replaced with French words: " + replacedWords.size());
	        System.out.println("Number of times each word was replaced: " + replaceCount);
	        System.out.println("Time taken to process: " + timeElapsed.toMillis() + " milliseconds");
	        System.out.println("Memory taken to process: " + memoryUsed + " bytes");
	    }
	}

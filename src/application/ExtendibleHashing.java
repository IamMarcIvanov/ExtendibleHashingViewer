package application;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ExtendibleHashing 
{
	int local_depth, bfr, global_depth;
	HashMap<String, ArrayList<Integer>> buckets;
	
	public static int hash_function_1(int key)
	{
		//hash function being used is h(k) = k % 10
		return key % 10;
	}
	public String last_few_bits(int a, int nos_bits)
	{
		String s1 = Integer.toBinaryString(a);
		int len_s1 = s1.length();
		if(len_s1 > nos_bits)
		{
			return s1.substring(len_s1 - nos_bits);
		}
		else if(len_s1 < nos_bits)
		{
			String s2 = s1;
			for(int i=0 ; i<(nos_bits-len_s1) ; i++)
			{
				s2 = "0".concat(s2);
			}
			return s2;
		}
		return s1;
	}
	public HashMap<String,ArrayList<String>> show_directories()
	{
		HashMap<String, ArrayList<String>> directory = new HashMap<String, ArrayList<String>>();
		for(int i=0 ; i<Math.pow(2, this.global_depth) ; i++)
		{
			String curr_dir = this.last_few_bits(i, this.global_depth);
			directory.put(curr_dir, new ArrayList<String>());
			for(String s : this.buckets.keySet())
			{
				if(s.equals(curr_dir.substring(curr_dir.length() - s.length())))
				{
					directory.get(curr_dir).add(s);
				}
			}
		}
		return directory;
	}
	public void add_element(int curr_nos)
	{
		boolean inserted = false;		
		int curr_hash = hash_function_1(curr_nos);
		if(buckets.isEmpty())
		{
			String bucketString = last_few_bits(curr_hash, this.local_depth);
			buckets.putIfAbsent(bucketString, new ArrayList<Integer>());
			buckets.get(bucketString).add(curr_nos);
		}
		else
		{
			int max_key_len = -1;
			for(String key : buckets.keySet())
			{
				if(key.length() > max_key_len)
				{
					max_key_len = key.length();
				}
			}
			String curr_key = last_few_bits(curr_hash, max_key_len);
			String[] key_array = buckets.keySet().toArray(new String[buckets.keySet().size()]);
			for(int i=0 ; i< key_array.length ; i++)
			{
				//System.out.println("For loop enetered");
				String relevant_substring = last_few_bits(curr_hash, key_array[i].length());
				if(key_array[i].equals(relevant_substring))
				{
					if(buckets.get(key_array[i]).size() < this.bfr)
					{
						buckets.get(key_array[i]).add(curr_nos);
						inserted = true;
					}
					else
					{
						int[] temp = new int[this.bfr];
						int j = 0;
						ArrayList<Integer> t = new ArrayList<Integer>();
						t = buckets.get(key_array[i]);
						Iterator<Integer> iter =  t.iterator();
						while(iter.hasNext())
						{
							temp[j] = iter.next();
							j++;
						}
						buckets.remove(key_array[i]);
						buckets.put("0" + key_array[i], new ArrayList<Integer>());
						buckets.put("1" + key_array[i], new ArrayList<Integer>());
						for(int val : temp)
						{
							String str2 = last_few_bits(val % 10, key_array[i].length() + 1);
							buckets.get(str2).add(val);
						}
						String str2 = last_few_bits(curr_hash, key_array[i].length() + 1);
						if(key_array[i].length() + 1 > this.global_depth)
						{
							this.global_depth++;
						}
						if(buckets.get(str2).size() < this.bfr)
						{
							buckets.get(str2).add(curr_nos);
							inserted = true;
						}
						else
						{
							System.out.println(this.buckets);
							add_element(curr_nos);
							inserted = true;
						}
					}
				}
			}
			if(buckets.get(curr_key) == null && !inserted)
			{
				String bucketString = last_few_bits(curr_hash, this.local_depth);
				buckets.putIfAbsent(bucketString, new ArrayList<Integer>());
				buckets.get(bucketString).add(curr_nos);
			}
		}
	}
	public String search(int x)
	{
		String[] key_array = this.buckets.keySet().toArray(new String[this.buckets.keySet().size()]);
		for(int i=0 ; i< key_array.length ; i++)
		{
			if(this.buckets.get(key_array[i]).contains(x))
			{
				return "The element " + x + " is in bucket " + key_array[i];
			}
		}
		return "Element not found";
	}
	public ExtendibleHashing(int global_depth, int local_depth, int bfr)
	{
		this.global_depth = global_depth;
		this.local_depth = local_depth;
		this.bfr = bfr;
	}
	
}

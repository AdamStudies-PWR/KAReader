package com.amazeum.kryptor;

import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;

class ConversionTables
{
	int[] charToInt = {497, 38, 148, 69, 388, 281, 34, 295, 232, 10, 32, 343, 375, 175, 7, 150, 214,
		197, 498, 416, 310, 195, 333, 274, 279, 2, 436, 507, 171, 369, 24, 9, 345, 109, 62, 85, 51,
		225, 500, 41, 123, 509, 110, 466, 64, 382, 449, 84, 390, 164, 351, 245, 502, 306, 101, 389,
		129, 40, 323, 79, 299, 35, 248, 304, 65, 107, 139, 356, 57, 404, 155, 368, 269, 475, 169,
		246, 280, 180, 236, 21, 192, 496, 207, 115, 354, 133, 499, 61, 162, 45, 329, 308, 379, 457,
		283, 99, 347, 503, 153, 112, 453, 141, 102, 117, 287, 294, 213, 89, 360, 413, 307, 42, 461,
		5, 478, 477, 319, 54, 263, 363, 81, 422, 193, 36, 244, 435, 492, 419, 282, 132, 154, 121,
		316, 255, 458, 393, 494, 140, 33, 445, 202, 27, 342, 394, 179, 240, 443, 488, 163, 292, 227,
		471, 432, 196, 361, 226, 160, 126, 181, 380, 261, 220, 49, 266, 298, 474, 262, 37, 411, 452,
		87, 59, 357, 446, 337, 311, 250, 387, 138, 359, 464, 489, 275, 270, 151, 501, 463, 131, 290,
		116, 481, 26, 267, 407, 277, 321, 444, 224, 330, 285, 486, 0, 212, 344, 397, 58, 428, 303,
		324, 228, 186, 125, 74, 118, 485, 247, 216, 385, 450, 230, 203, 442, 505, 415, 355, 11, 182,
		511, 200, 172, 455, 149, 335, 184, 468, 199, 400, 168, 448, 353, 30, 111, 506, 28, 264, 438,
		205, 12, 378, 215, 72, 331, 96, 338, 6, 495, 268, 130, 222, 454, 80, 147, 276, 328, 185,
		476, 332, 284, 424, 229, 318, 399, 364, 106, 320, 403, 176, 256, 417, 348, 238, 340, 456,
		510, 374, 221, 1, 427, 68, 187, 60, 189, 372, 490, 217, 173, 4, 242, 122, 161, 48, 243, 22,
		78, 108, 252, 293, 352, 13, 309, 317, 371, 95, 98, 473, 119, 254, 439, 398, 460, 92, 273,
		376, 194, 18, 44, 166, 341, 47, 433, 339, 157, 76, 144, 272, 288, 91, 174, 483, 82, 487,
		386, 209, 88, 137, 493, 377, 383, 73, 467, 414, 143, 29, 208, 142, 265, 315, 104, 334, 128,
		286, 86, 103, 19, 425, 257, 165, 75, 170, 3, 94, 134, 479, 50, 145, 350, 15, 349, 120, 188,
		39, 289, 431, 251, 218, 235, 440, 401, 231, 249, 451, 233, 392, 405, 301, 8, 491, 459, 297,
		55, 406, 258, 384, 237, 100, 67, 305, 152, 43, 465, 52, 420, 296, 434, 408, 362, 396, 470,
		312, 322, 278, 418, 198, 259, 206, 412, 234, 402, 156, 167, 326, 373, 223, 114, 113, 358,
		46, 219, 183, 325, 70, 77, 90, 291, 191, 17, 23, 146, 409, 124, 53, 300, 201, 336, 504, 426,
		462, 469, 480, 178, 190, 16, 429, 365, 484, 423, 63, 421, 327, 31, 346, 135, 395, 25, 71,
		14, 482, 211, 366, 127, 105, 210, 313, 441, 239, 260, 66, 159, 158, 381, 136, 177, 508, 20,
		391, 367, 56, 253, 93, 314, 83, 410, 271, 204, 437, 430, 370, 472, 241, 447, 302, 97
	};

	int[] intToChar = { 201, 286, 25, 369, 296, 113, 254, 14, 395, 31, 9, 225, 247, 308, 475, 376,
		461, 445, 324, 363, 493, 79, 302, 446, 30, 473, 191, 141, 243, 352, 240, 469, 10, 138, 6,
		61, 123, 167, 1, 380, 57, 39, 111, 408, 325, 89, 436, 328, 300, 162, 373, 36, 410, 450, 117,
		399, 496, 68, 205, 171, 290, 87, 34, 466, 44, 64, 486, 405, 288, 3, 440, 474, 250, 348, 212,
		367, 332, 441, 303, 59, 260, 120, 339, 500, 47, 35, 361, 170, 343, 107, 442, 336, 320, 498,
		370, 312, 252, 511, 313, 95, 404, 54, 102, 362, 357, 480, 273, 65, 304, 33, 42, 241, 99,
		434, 433, 83, 189, 103, 213, 315, 378, 131, 298, 40, 449, 211, 157, 479, 359, 56, 257, 187,
		129, 85, 371, 471, 490, 344, 178, 66, 137, 101, 354, 351, 333, 374, 447, 261, 2, 231, 15,
		184, 407, 98, 130, 70, 428, 331, 488, 487, 156, 299, 88, 148, 49, 366, 326, 429, 237, 74,
		368, 28, 229, 295, 337, 13, 276, 491, 459, 144, 77, 158, 226, 438, 233, 264, 210, 289, 379,
		291, 460, 444, 80, 122, 323, 21, 153, 17, 422, 235, 228, 452, 140, 220, 503, 246, 424, 82,
		353, 342, 481, 477, 202, 106, 16, 249, 216, 294, 384, 437, 161, 285, 258, 432, 197, 37, 155,
		150, 209, 269, 219, 388, 8, 391, 426, 385, 78, 403, 280, 484, 145, 508, 297, 301, 124, 51,
		75, 215, 62, 389, 176, 383, 305, 497, 316, 133, 277, 365, 401, 423, 485, 160, 166, 118, 244,
		355, 163, 192, 256, 72, 183, 502, 334, 321, 23, 182, 262, 194, 420, 24, 76, 5, 128, 94, 267,
		199, 360, 104, 335, 381, 188, 443, 149, 306, 105, 7, 412, 398, 164, 60, 451, 394, 510, 207,
		63, 406, 53, 110, 91, 309, 20, 175, 418, 482, 499, 356, 132, 310, 270, 116, 274, 195, 419,
		58, 208, 439, 430, 468, 263, 90, 198, 251, 266, 22, 358, 232, 453, 174, 253, 330, 281, 327,
		142, 11, 203, 32, 470, 96, 279, 377, 375, 50, 307, 239, 84, 224, 67, 172, 435, 179, 108,
		154, 415, 119, 272, 463, 478, 495, 71, 29, 506, 311, 292, 431, 284, 12, 322, 346, 248, 92,
		159, 489, 45, 347, 402, 217, 341, 177, 4, 55, 48, 494, 392, 135, 143, 472, 416, 204, 318,
		271, 236, 387, 427, 275, 69, 393, 400, 193, 414, 448, 501, 168, 425, 109, 350, 223, 19, 278,
		421, 127, 411, 467, 121, 465, 268, 364, 455, 287, 206, 462, 505, 382, 152, 329, 413, 125,
		26, 504, 245, 317, 386, 483, 221, 146, 196, 139, 173, 509, 238, 46, 218, 390, 169, 100, 259,
		230, 282, 93, 134, 397, 319, 112, 456, 186, 180, 409, 43, 349, 234, 457, 417, 151, 507, 314,
		165, 73, 265, 115, 114, 372, 458, 190, 476, 338, 464, 214, 200, 340, 147, 181, 293, 396,
		126, 345, 136, 255, 81, 0, 18, 86, 38, 185, 52, 97, 454, 222, 242, 27, 492, 41, 283, 227
	};
}

public class Converter
{
	public static TreeSet<Integer> eureka = new TreeSet<Integer>();
	private String version = "07102020";

	private static ConversionTables conversionTables = new ConversionTables();

	private int charToInt(char symbol)
	{
		int number = (int) symbol;

		if(number < conversionTables.charToInt.length)
		{
			return conversionTables.charToInt[number];
		}
		else return (int) '?';
	}

	private char intToChar (int number)
	{
		if (number>511) number-=512;
		if (number<0) number+=512;

		if(number < conversionTables.intToChar.length)
		{
			return (char) conversionTables.intToChar[number];
		}
		else return '?';
	}

	private LinkedList<Integer> shuffling(LinkedList<Integer> text , int var)
	{
		int half=(text.size()/2)+(text.size()%2);
		int choice;
		LinkedList<Integer> result	 = new LinkedList<Integer>();
		LinkedList<Integer> part1	 = new LinkedList<Integer>();
		LinkedList<Integer> part2	 = new LinkedList<Integer>();
		if(var<0)
		{
			choice=(-var)%4;
			choice= -1*choice;
			var=-1*var;
		} 
		else choice=var%4;
		
		switch(choice)
		{
			case -3:
				for(int i=0; i<text.size();i++)
				{
					result.add(text.get(i) - (i%(var/2)));
				}
				return result;
			
			case -1:
				for(int i=0; i<half;i++)
				{
					result.add(text.get(i));
					if(half+i<text.size())result.add(text.get(half+i));
				}
				return result;
			
			case 1:
				for(int i=0; i<text.size();i++)
				{
					if (i%2==1) part2.add(text.get(i));
					else part1.add(text.get(i));
				}
				result.addAll(part1);
				result.addAll(part2);
				return result;
				
			case -2:
			case 2:
				for(int i=1; i<=text.size();i++)
				{
					result.add(text.get(text.size()-i));
				}
				return result;
				
			case 3:
				for(int i=0; i<text.size();i++)
				{
					result.add(text.get(i) + (i%(var/2)));
				}
				return result;
				
			default:
				return text;
		}
	}
		
	public String encryption(String text)
	{
		//--------------------------------------------------------------23102018
		String result 				="";
		if (text.length()<1) return result;
		LinkedList<Integer> temp 	= new LinkedList<Integer>();
		Random random 				= new Random();
		
		
		int A1 = (random.nextInt(27)+44);						temp.add(A1);
		int A2 = (random.nextInt(27)+44);
		while(A1==A2) A2 = (random.nextInt(27)+44);				temp.add(A2);
		int A3 = (random.nextInt(27)+44);
		while(A3==A2||A3==A1) A3 = (random.nextInt(27)+44);
		int A4 = (random.nextInt(27)+44);
		int B1=1,B2=1,B3=1,B4=1;
		
		if		((A1+A2+A3+A4)%3==1) {B2*=-1;B4*=-1;}
		else if	((A1+A2+A3+A4)%3==2) {B1*=-1;B2*=-1;B3*=-1;B4*=-1;}
			
		for(int i=0; i<text.length();i++)
		{
			if		(i%4==0) temp.add(charToInt(text.charAt(i))+B1*A1);
			else if	(i%4==1) temp.add(charToInt(text.charAt(i))+B2*A2);
			else if	(i%4==2) temp.add(charToInt(text.charAt(i))+B3*A3);
			else if	(i%4==3) temp.add(charToInt(text.charAt(i))+B4*A4);
		}
		
																	temp.add(A3);
		
		temp = shuffling(temp,A4);
							
																	temp.add(A4);
		for(int var : temp)
		{
			result=result+this.intToChar(var);
		}
		
		
		return result;
	}
	
	public String decryption(String text)
	{
		String result 				="";
		if (text.length()<1) return result;
		LinkedList<Integer> temp 	= new LinkedList<Integer>();
		
		int A1, A2,A3,A4=charToInt(text.charAt(text.length()-1));
		int B1=1,B2=1,B3=1,B4=1;
		for(int i=0; i<text.length()-1;i++)	temp.add(charToInt(text.charAt(i)));
		temp=shuffling(temp, -A4);
		A1 = temp.get(0);				temp.remove(0);
		A2 = temp.get(0); 				temp.remove(0);
		A3 = temp.get(temp.size()-1);	temp.remove(temp.size()-1);

		if		((A1+A2+A3+A4)%3==1) {B2*=-1;B4*=-1;}
		else if	((A1+A2+A3+A4)%3==2) {B1*=-1;B2*=-1;B3*=-1;B4*=-1;}

		for(int i=0; i<temp.size();i++)
		{
			if		((i)%4==0) temp.set(i,temp.get(i)-(B1*A1));
			else if	((i)%4==1) temp.set(i,temp.get(i)-(B2*A2));
			else if	((i)%4==2) temp.set(i,temp.get(i)-(B3*A3));
			else if	((i)%4==3) temp.set(i,temp.get(i)-(B4*A4));
		}
		
		
		for(int var : temp) result=result+this.intToChar(var);
		
		return result;
	}

	public String getVersion() { return version; }
	
}
	
#### 1. Multipy the value eaach and add them

l = [1, 2, 3];
result = 1*2 + 1*3 + 2*3
```
List <Integer> nums = {1,2,3};
int result = 0;
int sum = 0;
for(int num: nums){
       result = (result+ (num*sum) );
        sum = (sum+num);
  }
	return result;
```
#### 2. Check string is paldrom or not
```
public boolean isPali(String s, int start, int end){
        while(start<end){
            if(s.charAt(start)!=s.charAt(end)){
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
```

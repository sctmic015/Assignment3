 # Using readline()
file1 = open('out.txt', 'r')
file2 = open('outBitSet.txt', 'r')
count = 0
 
while True:
    count += 1
 
    # Get next line from file
    line1 = file1.readline()
    line2 = file2.readline()
    line1 = line1.lower()
    line2 = line2.lower()
    line1 = line1.strip()
    line2 = line2.strip()
    
    if (line1 != line2):
        print(line1 + line2)
    # if line is empty
    # end of file is reached
    if not line1:
        break
 
file1.close()
print("complete")
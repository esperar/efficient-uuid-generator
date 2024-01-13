# Efficient UUID Generator

### Generating Sequential UUIDs
UUID V1 is created with the following information in each field:  
`Timestamp` - `Timestamp` - `Timestamp & Version` - `Variant & Clock sequence` - `Node id`

As it is a value generated based on time,  
arranging the order well allows for the creation of as sequential a value as possible.

The information in the 1st, 2nd, and 3rd fields comes from the lower 32 bits, middle 16 bits,  
and upper 12 bits of the timestamp, respectively.

Therefore, by arranging these in reverse order, it is possible to create UUIDs in chronological order.

```
Generators.timeBasedGenerator().generate();

/** Result
 * e8eb287d-b295-11ed-b062-6154355cf97a
 * e8eb4f8e-b295-11ed-b062-19583c733b0b
 * e8eb769f-b295-11ed-b062-3f042de161fb
 * e8eb76a0-b295-11ed-b062-99371b4b2895
 * e8eb76a1-b295-11ed-b062-bbe2a7590eb4
 * e8eb76a2-b295-11ed-b062-478ed313cf4e
 * e8eb76a3-b295-11ed-b062-71a927e2a90c
 * e8eb76a4-b295-11ed-b062-1797917006c1
 * e8eb76a5-b295-11ed-b062-2fa9ea5d72ea
 * e8eb76a6-b295-11ed-b062-8dca1bd336b5
 */
```

Looking at the values in the 1st, 2nd, and 3rd fields that are affected by the timestamp,  
we can see that the 2nd and 3rd fields remain the same, while only the 1st field increases.

By arranging these in reverse order, it is possible to generate UUIDs sequentially as intended. 
Another notable field is the 4th field. This field is based on the Clock Sequence and is not simply a sequence number.

It is a random number generated when the program starts, which increases over time, hence the name Clock Sequence.
When the server is restarted or scaled out, this field changes, so it cannot be assumed to always have the same value.

The 5th field is generated based on the server's Ethernet address and is currently being generated as a   
random number since it is not specifically provided.

Due to the nature of V1, which is based on the timestamp, it was determined that generating a   
random number to reduce the chance of duplication, rather than using a node ID or similar value that could result in duplication,
would be more efficient.

In summary, by arranging the fields in the order of 3 - 2 - 1 - 4 - 5,   
it is possible to have sequential values up to a certain point in the 3rd field. (This is not an absolute rule.)

The results are as follows:

<br>

### Minimizing UUID Size

Firstly, the dash ('-') used to separate each field is removed as it serves no purpose.  
This results in a total of 32 characters being generated.

When this is stored in a database, it will be used as a PK with a field of CHAR(32).   
This is generally 4 times larger than a standard BIGINT (8 bytes).

In the case of MySQL, when specified as a PK, it is automatically designated as an index,  
so continually storing a value that is 4 times larger becomes inefficient.

To improve this, converting it to Binary form as BINARY(16) instead of CHAR(32) reduces the size by half.

Of course, when stored in the database as a Binary type,  
a process of converting it into a human-readable value will be necessary when querying UUIDs in the future.

To encapsulate this conversion process, a Util class was created to separate  
the responsibilities related to UUID and make it usable where needed.
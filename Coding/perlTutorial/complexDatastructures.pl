use Data::Dumper qw(Dumper);

print '@Array: ' . Dumper \@an_array;
print '@Hash: ' . Dumper \%a_hash;
print '@reference: ' . Dumper $a_reference;

print Data::Dumper->Dump([\@an_array, \%a_hash, $a_reference],
[qw(an_array a_hash a_reference)]);
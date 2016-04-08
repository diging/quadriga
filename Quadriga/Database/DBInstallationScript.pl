#
#	Script to create the DB and schema
#

use Cwd qw();

my ($line, $userName,$dbName,$mysqlLocation);
system(">dbCreation.log");
#	Read the username and database name to create a database
open (MYFILE, 'database.props');
while($line=<MYFILE>){
	chomp($line);
	my @values = split('=', $line);
	if($line=~/dbuser/){
		$userName=$values[1];	
	}
	if($line=~/dbname/){
		$dbName=$values[1];
	}
    if($line=~/mysqllocation/){
		$mysqlLocation=$values[1];
	}
}
close(MYFILE);

unlink("./createDB.txt");
unlink("./createDBSchema.txt");

my $loc = Cwd::cwd();

#       Creates a file for creating Database
open (OUTFILE, '>createDB.txt');
print OUTFILE "create database if not exists $dbName;\n";
close(OUTFILE);


#	Creates a file for granting permissions to $userName
open (OUTFILE, '>>createDB.txt');
print OUTFILE "grant all privileges on $dbName.* to '".$userName."'\@'localhost';\n";
close(OUTFILE);


#	Creates a file for creating tables
open (OUTFILE, '>>createDBSchema.txt');
@files = <./Tables/*>;

foreach $file (@files) {
  print OUTFILE "source ".$loc."/".$file . "\n";
}
close(OUTFILE);


#	Creates a file for executing the foreign key constraint script
open (OUTFILE, '>>createDBSchema.txt');
@files = <./Scripts/*>;

foreach $file (@files) {
    print OUTFILE "source ".$loc."/".$file . "\n";
}
close(OUTFILE);


print "Creating Database : $dbName schema\n";
system("sudo $mysqlLocation < ./createDB.txt >> dbCreation.log 2>&1");
print "Creating tables on the database : $dbName\n";
print "Please enter your [$userName] user password when prompted\n";
system("sudo $mysqlLocation --user=$userName --password $dbName < ./createDBSchema.txt  >> dbCreation.log 2>&1" );

# Cleaning the file created by the script

unlink("./createDB.txt");
unlink("./createDBSchema.txt");


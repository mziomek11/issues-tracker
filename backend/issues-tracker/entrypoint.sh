cd issues-tracker
./mvnw install -Dmaven.repo.local=/usr/mvn -pl -Dmaven.test.skip=true -pl $MODULE_NAME -am
./mvnw spring-boot:run -Dmaven.repo.local=/usr/mvn -Dmaven.test.skip=true -pl $MODULE_NAME &
while true; do
  inotifywait -e modify,create,delete,move -r ./$MODULE_NAME && ./mvnw compile -Dmaven.repo.local=/usr/mvn -Dmaven.test.skip=true -pl $MODULE_NAME
done 

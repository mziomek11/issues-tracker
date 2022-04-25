cd issues-tracker
./mvnw spring-boot:run -pl $MODULE_NAME &
while true; do
  inotifywait -e modify,create,delete,move -r ./$MODULE_NAME && ./mvnw compile -Dmaven.test.skip=true -pl $MODULE_NAME
done 

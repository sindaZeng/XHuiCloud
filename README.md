- 打包命令 mvn package spring-boot:repackage

Spring Boot Maven plugin的最主要goal就是repackage，其在Maven的package生命周期阶段，能够将mvn package生成的软件包，再次打包为可执行的软件包，并将mvn package生成的软件包重命名为*.original。

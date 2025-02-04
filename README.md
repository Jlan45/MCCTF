# MCCTF

MCCTF是一个在我的世界中实现CTF平台的Bukkit插件，兼容Bukkit及衍生端服务器
## 功能

- 队伍管理
- 自定义题目
- 使用DockerAPI
- SQLite数据库存储队伍及玩家数据
## 环境要求

- Java 17
- Maven
- Minecraft server with Bukkit/Spigot/Paper 1.19 Version

## 安装

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/mcctf.git
    cd mcctf
    ```

2. Build the project using Maven:
    ```sh
    mvn clean package
    ```

3. 将`mcctf.jar` 文件从 `target` 目录拷贝至 `plugins` 目录.

4. 启动我的世界服务器

## Commands

- `/ctf` - 主要CTF相关命令
- `/ctfteam` - CTF队伍管理相关命令
- `/ctfmanage` - 管理环境

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## License

This project is licensed under the MIT License.
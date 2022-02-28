```bash
# 开启防火墙
service firewall start

# 重启
service firewall restart

# 关闭
service firewall stop



firewall-cmd --list-all   # 查看防火墙全部信息
firewall-cmd --list-ports # 只看端口信息


# 开启端口
firewall-cmd --zone=public --add-port=端口号/tcp --permanent

# 重启防火墙
systemctl restart firewalld.service
```







vm网络配置

```
https://www.bilibili.com/video/BV187411y7hF?p=18&spm_id_from=pageDriver
```


当我们想删除某个用户的时候，出现 user xxx is currently used by process xxx，可能的原因是你创建用户user1之后，使用su命令切换到user1用户下，之后又想删除user1用户，使用su root切换到root用户下，使用userdel user1。出现上述情况的根本原因在于切换回root用户之后，user1还被某个进程占用。

解决方案：ctrl+d（退出当前用户）

第一次使用ctrl+d退出root用户，回到user1用户；第二次使用ctrl+d退出user1用户，此时会返回到root用户（再按ctrl+d退出登陆连接），此时使用userdel user1正常删除。


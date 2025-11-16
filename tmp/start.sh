#!/bin/bash

# Always use this display
export DISPLAY=:0

# 1) If a stale X lock exists, remove it
if [ -f /tmp/.X0-lock ]; then
  echo "⚠️ Found stale /tmp/.X0-lock, removing..."
  rm -f /tmp/.X0-lock
fi

# 2) Start virtual X server
Xvfb :0 -screen 0 1280x720x16 &
echo "Xvfb started on :0"

# 3) Start XFCE desktop (in background)
startxfce4 >/tmp/xfce.log 2>&1 &
echo "XFCE started"

# 4) Start VNC server (no password for demo)
x11vnc -display :0 -rfbport 5900 -forever -shared -nopw >/tmp/x11vnc.log 2>&1 &
echo "x11vnc started on :0 (port 5900)"

# 5) Start noVNC (websockify)
websockify --web=/usr/share/novnc/ 6080 localhost:5900 >/tmp/novnc.log 2>&1 &
echo "noVNC listening on :6080"

# 6) Start MariaDB
service mariadb start
echo "MariaDB started"

echo "========================================="
echo "Desktop: http://localhost:6080"
echo "MariaDB: mysql -u root"
echo "========================================="

# 7) Keep container alive
tail -f /dev/null

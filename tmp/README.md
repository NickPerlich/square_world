# CSC 509 – Ubuntu + MariaDB + noVNC Desktop

This Docker image provides a lightweight **Ubuntu 22.04 desktop** (XFCE) accessible through a web browser, with **MariaDB** pre-installed.  
It’s designed for **CSC 509 students** to explore software engineering tools and database integration in a full Linux environment — all from within their browser.


## 🚀 Getting Started

### 1️⃣ Pull the image
```bash
docker pull mscjaviergs/csc509:latest
```

### 2️⃣ Run the container
```bash
docker run -d --name csc509   -p 6080:6080 -p 3306:3306   mscjaviergs/csc509
```

- `6080` → Browser access (noVNC)  
- `3306` → MariaDB server port  


## 🌐 Access the Desktop

Open your browser and go to:

👉 **http://localhost:6080**

You’ll see an XFCE desktop session running inside the container.


## 💾 Access MariaDB

Inside the container (or via your host if you map port 3306):

```bash
mysql -u root
```

- **User:** root  
- **Password:** *(none)*  
- **Database:** demo (if initialized)

You can connect from external clients using:
```bash
mysql -h 127.0.0.1 -P 3306 -u root
```

## 🧩 What’s Included
- Ubuntu 22.04 LTS  
- XFCE desktop environment  
- x11vnc + noVNC (web-based desktop)  
- MariaDB server  
- Basic developer utilities (`curl`, `net-tools`, etc.)


## 👨‍🏫 Instructor Notes
This container is intended for educational use only.  
Each student runs it locally and connects via **http://localhost:6080** — no external accounts required.  

To extend it, students can:
- install new Linux packages inside the container,
- connect Python or Java applications to the MariaDB service,
- and commit customized images using:
  ```bash
  docker commit csc509 mscjaviergs/csc509:studentname
  ```


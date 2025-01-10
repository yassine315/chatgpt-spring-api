# **README - Déploiement de l'application Spring Boot dans Kubernetes**

---

### **1. Construire l'image Docker :**
```bash
docker build -t yassine315/chatgpt-spring-api:latest .
```

---

### **2. Pousser l'image sur Docker Hub :**
```bash
docker push yassine315/chatgpt-spring-api:latest
```

---

### **3. Créer le namespace Kubernetes (optionnel) :**
```bash
kubectl create namespace chatgpt-app
```

---

### **4. Appliquer le `deployment.yaml` :**
```bash
kubectl apply -f k8s/deployment.yaml
```

---

### **5. Appliquer le `service.yaml` :**
```bash
kubectl apply -f k8s/service.yaml
```

---

### **6. Vérifier que les pods sont en cours d'exécution :**
```bash
kubectl get pods
```

---

### **7. Vérifier le service :**
```bash
kubectl get svc
```

---

### **8. Si EXTERNAL-IP est `<pending>` (pour Minikube) :**
```bash
minikube tunnel
```

---

### **9. Accéder à l'application :**
Ouvre un navigateur :
```
http://<external-ip>:8080
```

---

### **10. Supprimer le déploiement :**
```bash
kubectl delete -f k8s/deployment.yaml
kubectl delete -f k8s/service.yaml
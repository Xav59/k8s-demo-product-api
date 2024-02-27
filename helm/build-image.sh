# See https://www.baeldung.com/docker-local-images-minikube
eval $(minikube -p minikube docker-env)
../mvnw package -f ../pom.xml
eval $(minikube -p minikube docker-env -u)


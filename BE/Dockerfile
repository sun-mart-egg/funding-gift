FROM gradle:8.5-jdk17 as builder
WORKDIR /app

# Gradle 빌드에 필요한 파일들 복사
COPY build.gradle settings.gradle /app/
COPY src /app/src

# 빌드 수행
RUN gradle build -x test --parallel --continue

FROM openjdk:17
EXPOSE 8081

# 이미지 생성 시 파일 복사
COPY --from=builder /app/build/libs/*.jar /app/app.jar

ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV EC2_PUBLIC_IP=${EC2_PUBLIC_IP}
ENV JWT_SECRET=${JWT_SECRET}
ENV KAKAO_CLIENT_ID=${KAKAO_CLIENT_ID}
ENV KAKAO_CLIENT_SECRET=${KAKAO_CLIENT_SECRET}
ENV REDIS_PASSWORD=${REDIS_PASSWORD}
ENV REDIS_PORT=${REDIS_PORT}

#CMD ["java", "-jar", "/app/app.jar", "--DB_PASSWORD=${DB_PASSWORD}", "--DB_URL=${DB_URL}", "--DB_USERNAME=${DB_USERNAME}", "--EC2_PUBLIC_IP=${EC2_PUBLIC_IP}", "--JWT_SECRET=${JWT_SECRET}", "--KAKAO_CLIENT_ID=${KAKAO_CLIENT_ID}", "--KAKAO_CLIENT_SECRET=${KAKAO_CLIENT_SECRET}", "--REDIS_PASSWORD=${REDIS_PASSWORD}", "--REDIS_PORT=${REDIS_PORT}"]
CMD ["java", "-jar", "/app/app.jar"]
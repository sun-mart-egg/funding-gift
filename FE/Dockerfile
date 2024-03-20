# FROM node:20.10.0

# WORKDIR /app
# COPY . .
# RUN npm install

# RUN npm run build

# EXPOSE 5173
# # CMD ["serve", "-l","5173","-s", "dist"]
# ENTRYPOINT ["npm", "run","dev"]

# 1단계: 애플리케이션 빌드
FROM node:20.10.0 as builder
WORKDIR /app

# 필요한 파일들 복사
COPY package*.json ./
RUN npm install

# 빌드 수행
COPY . .
RUN npm run build || exit 1

# 2단계: 빌드된 애플리케이션 실행
FROM node:20.10.0
WORKDIR /app

# 빌드된 애플리케이션 복사
COPY --from=builder /app/dist /app/dist

# serve 설치
RUN npm install -g serve

# 5173 포트 노출
EXPOSE 5173

# 애플리케이션 실행
ENTRYPOINT ["serve", "-l", "5173", "-s", "dist"]
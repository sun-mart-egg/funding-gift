FROM node:20.10.0

WORKDIR /app
COPY . .
RUN npm install

RUN npm run build

EXPOSE 5173
# CMD ["serve", "-l","5173","-s", "dist"]
ENTRYPOINT ["npm", "run","dev"]
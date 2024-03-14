/** @type {import('tailwindcss').Config} */
const config = {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx,ts,tsx}"
  ],
  theme: {
    extend: {
      width: {},
      height: {},
      colors: {
        'cusColor1': '#0802A3', // 로고, 뒤로가기 등등
        'cusColor2': '#91D54E', // 달력 이모티콘
        'cusColor3': '#FF7676', // 진짜 제일 많이쓰는 색깔
        'cusColor4': '#FFCD4B', // 우리가 설정한 노란색
        'cusColor5': '#40AEFF', // 우리가 설정한 파란색
        'cusColor6': '#C6F895', // 프로그레스 바 색상
      }
    },
  },
  plugins: [],
}

for (let i = 0.5; i <= 100; i+= 0.5) {
  config.theme.extend.width[`${i}p`] = `${i}%`;
  config.theme.extend.height[`${i}p`] = `${i}%`;
}

export default config;

/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx,ts,tsx}"
  ],
  theme: {
    extend: {
      colors: {
        'cusColor1': '#0802A3', // 로고, 뒤로가기 등등
        'cusColor2': '#91D54E', // 달력 이모티콘
        'cusColor3': '#FF7676', // 진짜 제일 많이쓰는 색깔
        'cusColor4': '#FFCD4B', // 우리가 설정한 노란색
        'cusColor5': '#40AEFF', // 우리가 설정한 파란색
        'cusColor6': '#C6F895', // 프로그레스 바 색상
      },
      fontFamily: {
        cusFont1: ['cusFont1'], // 산토끼체
        cusFont2: ['cusFont2'], // 나눔 스퀘어 BOLD
        cusFont3: ['cusFont3'], // 나눔 스퀘어 REGULAR
        cusFont4: ['cusFont4'], // 온글잎 시우체
        cusFont5: ['cusFont5'], // 호빵체
        cusFont6: ['cusFont6'], // 영도체
      }
    },
  },
  plugins: [],
}


import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";
import { VitePWA } from "vite-plugin-pwa";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react(),
    VitePWA({
      registerType: "autoUpdate",
      // devOptions: {
      //   enabled: true
      // },
      workbox: {
        globPatterns: ["**/*.{js,jsx,ico,png,svg}"],
      },
      manifest: {
        name: "생선가게",
        short_name: "생선가게",
        description: "생일 선물 가치줄 게",
        start_url: "/",
        display: "standalone",
        theme_color: "#000000",
        icons: [
          {
            src: "/icons/size-16.png",
            sizes: "16x16",
          },
          {
            src: "/icons/size-48.png",
            sizes: "48x48",
          },
          {
            src: "/icons/size-72.png",
            sizes: "72x72",
          },
          {
            src: "/icons/size-128.png",
            sizes: "128x128",
          },
          {
            src: "/icons/size-144.png",
            sizes: "144x144",
          },
          {
            src: "/icons/size-152.png",
            sizes: "152x152",
          },
          {
            src: "/icons/size-192.png",
            sizes: "192x192",
            purpose: "maskable",
          },
          {
            src: "/icons/size-512.png",
            sizes: "512x512",
            purpose: "maskable",
          },
        ],
      },
    }),
  ],
  assetsInclude: ["**/*.PNG"],
});

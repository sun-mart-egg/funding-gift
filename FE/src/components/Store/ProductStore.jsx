// Store/ProductStore.js
import create from "zustand";

const useProductStore = create((set) => ({
  product: null,
  setProduct: (product) => set({ product }),

  option: null,
  setOption: (option) => set({ option }),

  // 상태 초기화 함수
  resetProductData: () =>
    set({
      product: null,
      option: null,
    }),
}));

export default useProductStore;

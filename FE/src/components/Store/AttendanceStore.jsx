import { create } from "zustand";

const useAttendanceStore = create((set) => ({
  fundingId: null,
  sendMessage: "",
  sendMessageTitle: "",
  price: "",
  fundingImg: "",
  fundingTitle: "",
  productName: "",
  consumerName: "",

  // 상태를 업데이트하는 함수
  updateUserStore: (key, value) =>
    set((state) => ({
      ...state,
      [key]: value,
    })),

  // 상태 초기화 함수
  resetUserStore: () =>
    set({
      fundingId: null,
      sendMessage: "",
      sendMessageTitle: "",
      price: "",
      fundingImg: "",
      fundingTitle: "",
      productName: "",
      consumerName: "",
    }),
}));

export default useAttendanceStore;

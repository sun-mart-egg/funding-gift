// store.js
import create from "zustand";

export const useStore = create((set) => ({
  selectedAnniversary: null,
  setSelectedAnniversary: (anniversary) =>
    set({ selectedAnniversary: anniversary }),

  contentIndex: 0,
  setContentIndex: (index) => set({ contentIndex: index }),

  selectedIndex: null,
  setSelectedIndex: (index) => set({ selectedIndex: index }),

  // 상태 초기화 액션 추가
  reset: () =>
    set({ selectedAnniversary: null, contentIndex: 0, selectedIndex: null }),
}));

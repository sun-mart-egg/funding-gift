// store.js
import { create } from "zustand";

export const useStore = create((set) => ({
  selectedAnniversary: null,
  setSelectedAnniversary: (anniversary) =>
    set({ selectedAnniversary: anniversary }),

  selectedAddress: null,
  setSelectedAddress: (address) => set({ selectedAddress: address }),

  selectedAccount: null,
  setSelectedAccount: (account) => set({ selectedAccount: account }),

  contentIndex: 0,
  setContentIndex: (index) => set({ contentIndex: index }),

  selectedAnniversaryIndex: null,
  setSelectedAnniversaryIndex: (index) =>
    set({ selectedAnniversaryIndex: index }),

  selectedAddressIndex: null,
  setSelectedAddressIndex: (index) => set({ selectedAddressIndex: index }),

  selectedAccountIndex: null,
  setSelectedAccountIndex: (index) => set({ selectedAccountIndex: index }),

  // 상태 초기화 액션 추가
  reset: () =>
    set({
      selectedAnniversary: null,
      selectedAddress: null,
      selectedAccount: null,
      contentIndex: 0,
      selectedAnniversaryIndex: null,
      selectedAddressIndex: null,
      selectedAccountIndex: null,
    }),
}));

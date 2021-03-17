library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity uart_tx is
  generic (N: integer := 115_200);
  Port (Clk: in std_logic;
        Rst: in std_logic;
        TxData: in std_logic_vector(7 downto 0);
        Start: in std_logic;
        Tx: out std_logic;
        TxRdy: out std_logic );
end uart_tx;

architecture Behavioral of uart_tx is

type TIP_STARE is (ready, load, send, waitbit, shift);
signal St : TIP_STARE;

constant Clk_frequency : integer := 100_000_000;
constant T_BIT : integer := Clk_frequency/N;

signal CntRate : integer := 0;
signal CntBit : integer := 0;

signal LdData : std_logic := '0'; 
signal ShData: std_logic := '0';
signal TxEn : std_logic := '0';

signal TSR : std_logic_vector(9 downto 0) := (others => '0');

attribute keep : STRING;
attribute keep of St, CntBit, CntRate, TSR : signal is "TRUE";

begin

 proc_control: process (Clk)
 begin
    if RISING_EDGE (Clk) then
        if (Rst = '1') then
             St <= ready;
        else
            case St is
                when ready =>
                    CntRate <= 0;
                    CntBit <= 0;
                    if (Start = '1') then
                        St <= load;
                    end if;
                when load =>
                    St <= send;
                when send =>
                    CntBit <= CntBit + 1;
                    St <= waitbit;
                when waitbit =>
                    CntRate <= CntRate + 1;
                    if (CntRate = T_BIT-3) then
                        CntRate <= 0;
                        St <= shift;
                    end if;
                when shift =>
                    if (CntBit = 10) then
                        St <= ready;
                    else
                        St <= send;
                    end if;
                when others =>
                    St <= ready;
            end case;
        end if;
    end if;
 end process proc_control;
 
-- Setarea semnalelor de comanda
 LdData <= '1' when St = load else '0';
 ShData <= '1' when St = shift else '0';
 TxEn <= '0' when St = ready or St = load else '1';
 
-- Setarea semnalelor de iesire
 Tx <= TSR(0) when TxEn = '1' else '1';
 TxRdy <= '1' when St = ready else '0'; 

 shift_register: process(Clk, Rst)
 begin
    if (Rising_Edge(Clk)) then
        if Rst = '1' then
            TSR <= (others => '0');
        elsif LdData = '1' then 
            TSR(8 downto 1) <= TxData;
            TSR(0) <= '0';
            TSR(9) <= '1';
        elsif ShData = '1' then
            TSR <= '0' & TSR(9 downto 1);
        end if;
    end if;
 end process;


end Behavioral;

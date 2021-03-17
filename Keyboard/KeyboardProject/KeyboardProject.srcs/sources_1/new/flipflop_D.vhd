library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity flipflop_D is
  Port (Clk: in std_logic;
	    D: in std_logic;
		Q: out std_logic);
end flipflop_D;

architecture Behavioral of flipflop_D is
signal Q_temp: std_logic;

begin

process (Clk) begin
    if rising_edge(Clk) then
        Q_temp <= D;
    end if;
end process;

Q <= Q_temp;

end Behavioral;